package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController // Serve para indicar que a classe é um controlador REST
@RequestMapping(value = "/products") // Define o endpoint base para os mapeamentos deste controlador
public class ProductController {

    @Autowired // Serve para injetar a dependência do repositório de produtos
    private ProductService service;

    @GetMapping(value = "/{id}")  // Serve para mapear requisições HTTP GET para este método
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) { // Serve para indicar que o parâmetro id será extraído do caminho da URL
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) { // O ResponseEntity encapsula a resposta HTTP, permitindo configurar o status e o corpo da resposta
        Page<ProductDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping // Serve para mapear requisições HTTP POST para este método
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) { // Serve para indicar que o corpo da requisição será convertido em um objeto ProductDTO
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto); // Retorna uma resposta HTTP 201 Created com o URI do novo recurso no cabeçalho Location
    }

    @PutMapping(value = "/{id}")  // Serve para mapear requisições HTTP PUT para este método
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) { // Serve para indicar que o parâmetro id será extraído do caminho da URL
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }
}
