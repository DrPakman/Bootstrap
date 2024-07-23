package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.service.RoleService;
import habsida.spring.boot_security.demo.service.UserService;
import habsida.spring.boot_security.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(value = "activeTab", required = false) String activeTab) {
        List<User> users = userService.index();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("activeTab", activeTab != null ? activeTab : "nav-home-tab");
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "users/index";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam List<String> roles) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/index?activeTab=nav-profile-tab";
        }
        userService.createUserWithRoles(user, roles);
        return "redirect:/admin/index?activeTab=nav-home-tab";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id, @RequestParam List<String> roles) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userService.updateUserWithRoles(id, user, roles);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/index";
    }

    @GetMapping()
    public String adminHome() {
        return "users/adminPage";
    }
}
