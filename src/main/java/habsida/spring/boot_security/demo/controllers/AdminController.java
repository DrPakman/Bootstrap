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
        return "users/index";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/index";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user, @RequestParam List<String> roleNames) {
        List<Role> roles = roleService.findRolesByNames(roleNames);
        for (Role role : roles) {
            user.addRole(role);
        }
        userService.createUser(user);
        return "redirect:/admin";
    }

@PatchMapping("/{id}/edit")
public String update(@ModelAttribute User user, @PathVariable int id, @RequestParam List<String> roleNames) {
    User existingUser = userService.getUserByUsername(id);

    existingUser.setUsername(user.getUsername());
    existingUser.setLastname(user.getLastname());
    existingUser.setAge(user.getAge());
    existingUser.setEmail(user.getEmail());
    existingUser.setPassword(user.getPassword());

    List<Role> roles = roleService.findRolesByNames(roleNames);
    existingUser.getRoles().clear();
    for (Role role : roles) {
        existingUser.addRole(role);
    }

    // Сохраняем обновленного пользователя
    userService.updateUser(existingUser);
    return "redirect:/admin"; // Перенаправление на страницу администрирования
}


    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
