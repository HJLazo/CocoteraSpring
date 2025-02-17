package com.cocotera.controller;

import com.cocotera.interfaces.IClientRepository;
import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class ClientController {

    @Autowired
    private IClientRepository clientRepository;

    @GetMapping("/clients")
    public String showClients(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("client", new Client());
        return "clients";
    }

    @PostMapping("/addClient")
    public String addClient(@ModelAttribute Client client) {
        client.setClientId(UUID.randomUUID().toString());
        clientRepository.save(client);
        return "redirect:/clients";
    }
}
