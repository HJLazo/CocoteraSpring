package com.cocotera.controller;

import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Display the form and the product list
    @GetMapping("/products")
    public String showProducts(Model model) {
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
