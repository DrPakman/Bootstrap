package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.service.RoleService;
import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("")
    public String index(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        List<User> users = userService.Index();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allRoles", roleService.findAllRoles());
//        model.addAttribute("activeTab", activeTab != null ? activeTab : "nav-home-tab");
        return "users/index2";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        User user = userService.getUserByUsername(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "users/index2";
    }

    @PostMapping("/new")
    public String SaveUser(@ModelAttribute("user") User user, @RequestParam("roles") String[] roleNames) {
        List<Role> roles = roleService.findRoleByNames(Arrays.asList(roleNames));
        user.setRoles(new ArrayList<>(roles));
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserByUsername(id));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id, @RequestParam List<String> roles) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
