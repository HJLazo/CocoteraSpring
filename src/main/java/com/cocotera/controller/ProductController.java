package com.cocotera.controller;

import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Product;
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
public class ProductController {

    @Autowired
    private IProductsRepository productRepository;

    @GetMapping("/products")
    public String showProducts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        return "products";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productRepository.save(product);
        return "redirect:/products";
    }
}
