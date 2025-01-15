package ch.bdr.ImdbLike.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Utilisateur user, Model model) {
        userRepository.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String pseudo, @RequestParam String password, HttpSession session, Model model) {
        Optional<Utilisateur> user = userRepository.findByPseudo(pseudo);
        if (user.isPresent() && user.get().getMdp().equals(password)) {
            session.setAttribute("user", user.get());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid pseudo or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}