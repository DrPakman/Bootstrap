package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    public String userHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        // добавить логику для отображения данных пользователя
        return "users/show";
    }

    @GetMapping("/showInfo")
    public String showInfo () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User personDetails = (User) authentication.getPrincipal();
        System.out.println(personDetails.getUsername()+" "+personDetails.getPassword());
        return "user";
    }

}
