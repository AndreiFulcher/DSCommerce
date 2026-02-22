package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product,Long> { // Serve para definir um repositório JPA para a entidade Product com chave primária do tipo Long

    @Query(value = "SELECT * FROM tb_product WHERE name ILIKE CONCAT('%', :name, '%')", nativeQuery = true)
    Page<Product> searchByName(String name, Pageable pageable);
}
