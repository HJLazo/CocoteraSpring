package com.cocotera.controller;

import com.cocotera.dto.OrderDTO;
import com.cocotera.dto.OrderItemDTO;
import com.cocotera.interfaces.IClientRepository;
import com.cocotera.interfaces.IOrderItemRepository;
import com.cocotera.interfaces.IOrderRepository;
import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Client;
import com.cocotera.models.Order;
import com.cocotera.models.OrderItem;
import com.cocotera.models.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
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

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @GetMapping("/orders")
    public String showOrderForm(Model model, HttpSession session) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
//
//        if (isAuthenticated) {
//            System.out.println("Authenticated User: " + authentication.getName());
//            System.out.println(authentication.getDetails());
//        } else {
//            System.out.println("No user is authenticated.");
//        }



//        System.out.println("-------------------------");
//        System.out.println(isAuthenticated);
//        System.out.println("-------------------------");
        List<Client> clients = clientRepository.findAll();
        List<Product> products = productsRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("clients", clients);
//        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("orderDTO", new OrderDTO());
        return "orders";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute OrderDTO orderDTO, Model model) {
        Order order = new Order();
        try {
            order.setOrderId(UUID.randomUUID().toString());

            Client client = clientRepository.findById(orderDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found: " + orderDTO.getClientId()));


            order.setClient(client);

            order.setTotal(BigDecimal.ZERO);

            // Save the order
            order = orderRepository.save(order);
        } catch (Exception e) {
            System.out.println("Error saving order: " + e.getMessage());
            throw new RuntimeException("Error saving order: " + e.getMessage());
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            if (itemDTO.getQuantity() > 0) {
                try {
                    Product product = productsRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.getProductId()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderItemId(UUID.randomUUID().toString());
                    orderItem.setOrderId(order.getOrderId());
                    orderItem.setProductId(product.getProductId());
                    orderItem.setQuantity(itemDTO.getQuantity());

                    BigDecimal itemTotalPrice = BigDecimal.valueOf(product.getPrice()).multiply(new BigDecimal(itemDTO.getQuantity()));
                    orderItem.setPrice(itemTotalPrice);

                    total = total.add(itemTotalPrice);

                    orderItemRepository.save(orderItem);
                } catch (Exception e) {
                    // Handle the error (log, return an error message, etc.)
                    System.out.println("Error saving order item: " + e.getMessage());
                    throw new RuntimeException("Error saving order item for product " + itemDTO.getProductId() + ": " + e.getMessage());
                }
            }
        }

        try {
            order.setTotal(total);
            orderRepository.save(order);
        } catch (Exception e) {
            System.out.println("Error updating order total: " + e.getMessage());
            throw new RuntimeException("Error updating order total: " + e.getMessage());
        }

        return "redirect:/orderList";
    }


    @GetMapping("/orderList")
    public String showOrderList(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orderList";
    }
}
