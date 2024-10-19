package com.cocotera.controller;
import com.cocotera.interfaces.IUserRepository;
import com.cocotera.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import java.util.UUID;

@Controller
public class RegistroUsuarioController {



    @Autowired
    private IUserRepository registroRepository;

    @GetMapping("/registroUsuario")
    public String registroUsuario(Model model){
        model.addAttribute("user", new User());
        return "registroUsuario";
    }

    @PostMapping("/registroUsuario")
    public String registerUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setRole("cliente");
        registroRepository.save(user);
        return "redirect:/coleccion";
    }
}
