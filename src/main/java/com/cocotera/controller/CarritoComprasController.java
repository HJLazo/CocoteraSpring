package com.cocotera.controller;

import java.util.List;

import com.cocotera.interfaces.IProductsRepository;
import com.cocotera.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class CarritoComprasController {

	@Autowired
	private IProductsRepository productsRepository;


	   @GetMapping("/coleccion")
	    public String listaProductos(Model model){
		   List<Product> productos = productsRepository.findAll();
		   model.addAttribute("productos", productos);
	       return "coleccion";
	    }
	   
	   @GetMapping("/carritoCompras")
	    public String cargarCarrito(){

	        return "carritoCompras";
	    }
}
