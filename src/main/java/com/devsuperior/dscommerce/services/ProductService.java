package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Serve para registrar a classe como um componente de serviço no Spring
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // Serve para definir o escopo da transação como somente leitura
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) { // Serve para buscar todos os produtos com paginação
        Page<Product> result = repository.findAll(pageable); // Busca todos os produtos do repositório com base na página solicitada
        return result.map(ProductDTO::new); // Converte cada entidade Product em um ProductDTO
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) { // Serve para inserir um novo produto no banco de dados
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity = repository.save(entity); // Salva a entidade do produto no repositório
        return new ProductDTO(entity); // Retorna o DTO do produto inserido
    }
}
