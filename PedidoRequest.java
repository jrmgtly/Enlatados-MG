package com.proyectofinal.jrmg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador que maneja la raíz de la aplicación.
 * Redirige "/" a la página de login estática.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "forward:/login.html";
    }
}
