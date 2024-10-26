package com.cocotera.controller;

import com.cocotera.interfaces.IUserRepository;
import com.cocotera.models.Client;
import com.cocotera.models.User;
import com.cocotera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/users")
    public String showClients(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/addUser")
    public String addClient(@ModelAttribute User user) {
        user.setUserId(UUID.randomUUID().toString());
        userRepository.save(user);
        return "redirect:/users";
    }
}
