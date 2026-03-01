package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Category;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) { // Construtor que recebe os campos id e name para inicializar o DTO
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) { // Construtor que recebe uma entidade de categoria e inicializa os campos do DTO com os valores correspondentes
        id = entity.getId();
        name = entity.getName();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
