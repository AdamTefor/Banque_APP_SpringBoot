package ma.banque.mybanque.controller;

import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Page de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Redirection après connexion selon le rôle
    @GetMapping("/")
    public String redirectionParRole(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.trouverParEmail(principal.getName());
        model.addAttribute("user", user);

        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/client/dashboard";
        }
    }

    // Page logout optionnelle (si tu veux afficher un message)
    @GetMapping("/logout-success")
    public String logoutPage() {
        return "redirect:/login?logout";
    }
}
