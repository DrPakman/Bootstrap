package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.dao.UserDao;
import habsida.spring.boot_security.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class AdminController {

    private final UserDao userDao;
    @Autowired
    public AdminController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public String index (Model model) {
        model.addAttribute("users", userDao.index());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDao.show(id));
        return "users/show";
    }
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }
    @PostMapping("/new")
    public String create (@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/newUser";
        }
        userDao.save(user);
        return "redirect:/users";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.show(id));
        return "users/editUser";
    }
    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userDao.update(id, user);
        return "redirect:/index";
    }
    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/user")
    public String userHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        // добавить логику для отображения данных пользователя
        return "users/show";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "users/adminPage";
    }




}
