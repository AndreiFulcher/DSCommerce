package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourseNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service // Serve para registrar a classe como um componente de serviço no Spring
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // Serve para definir o escopo da transação como somente leitura
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Recurso não encontrado")); // Busca o produto pelo ID no repositório, lançando uma exceção se não for encontrado
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
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity); // Salva a entidade do produto no repositório
        return new ProductDTO(entity); // Retorna o DTO do produto inserido
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) { // Serve para inserir um novo produto no banco de dados
        try{
            Product entity = repository.getReferenceById(id); // Obtém uma referência à entidade do produto com o ID fornecido
            copyDtoToEntity(dto, entity); // Copia os dados do DTO para a entidade
            entity = repository.save(entity); // Salva a entidade do produto no repositório
            return new ProductDTO(entity); // Retorna o DTO do produto atualizado
        } catch (EntityNotFoundException e) {
            throw new ResourseNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourseNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
