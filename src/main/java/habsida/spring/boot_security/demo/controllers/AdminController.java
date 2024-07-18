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
    public String index (Model model) {
        List<User> users = userService.index();
        model.addAttribute("users",users);
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "users/show";
    }
    @GetMapping("/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "users/index";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         @RequestParam(value = "roles", required = false) List<String> roles,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAllRoles());
            return "users/index";
        }

        userService.save(user, roles != null ? roles : List.of());
        return "redirect:/admin/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.show(id));
        return "users/editUser";
    }
    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userService.update(id, user);
        return "redirect:/admin/index";
    }
    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/index";
    }

    @GetMapping()
    public String adminHome() {
        return "users/adminPage";
    }




}
