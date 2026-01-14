package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController // Serve para indicar que a classe é um controlador REST
@RequestMapping(value = "/products") // Define o endpoint base para os mapeamentos deste controlador
public class ProductController {

    @Autowired // Serve para injetar a dependência do repositório de produtos
    private ProductService service;

    @GetMapping(value = "/{id}")  // Serve para mapear requisições HTTP GET para este método
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
