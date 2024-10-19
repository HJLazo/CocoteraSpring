package com.cocotera.controller;

import com.cocotera.interfaces.IClientRepository;
import com.cocotera.interfaces.IOrderRepository;
import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Client;
import com.cocotera.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IProductsRepository productsRepository;

    @GetMapping("/orders")
    public String showOrders(Model model){
        List<Order> orders = orderRepository.findAll();
        List<Client> clients = clientRepository.findAll(); // to select a client
        model.addAttribute("orders", orders);
        model.addAttribute("clients", clients);
        model.addAttribute("order", new Order()); // new empty order for the form
        return "orders"; // Thymeleaf template name
    }

    public String addOrder(@ModelAttribute Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        orderRepository.save(order);
        return "redirect:/orders"; // Redirect to the orders page
    }
}
