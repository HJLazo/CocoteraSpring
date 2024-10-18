package com.cocotera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CarritoComprasController {
	   @GetMapping("/coleccion")
	    public String cargarProductos(){

	        return "coleccion";
	    }
	   
	   @GetMapping("/carritoCompras")
	    public String cargarCarrito(){

	        return "carritoCompras";
	    }
}
