package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String helloUser(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "users/userPage";
    }
}
