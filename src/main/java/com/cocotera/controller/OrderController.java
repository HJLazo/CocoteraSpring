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
import org.springframework.beans.factory.annotation.Autowired;
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
    public String showOrderForm(Model model) {
        List<Product> products = productsRepository.findAll();
        model.addAttribute("products", products);
        return "orders";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute OrderDTO orderDTO, Model model) {
        Order order = new Order();
        order.setClientId(orderDTO.getClientId());
        order.setTotal(BigDecimal.ZERO);
        Order savedOrder = orderRepository.save(order);


        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            if (itemDTO.getQuantity() > 0) {
                Product product = productsRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));


                if (product.getPrice() == 0) {
                    throw new RuntimeException("Product price cannot be null");
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(savedOrder.getOrderId());
                orderItem.setProductId(product.getProductId());
                orderItem.setQuantity(itemDTO.getQuantity());


                BigDecimal itemTotalPrice = BigDecimal.valueOf(product.getPrice());
                orderItem.setPrice(itemTotalPrice);

                total = total.add(itemTotalPrice);
                orderItemRepository.save(orderItem);
            }
        }

        savedOrder.setTotal(total);
        orderRepository.save(savedOrder);

        model.addAttribute("order", savedOrder);
        return "orderSuccess";
    }
}
