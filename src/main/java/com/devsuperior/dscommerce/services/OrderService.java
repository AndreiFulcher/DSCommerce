package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.devsuperior.dscommerce.entities.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true) // Serve para definir o escopo da transação como somente leitura
    public OrderDTO findById(Long id) {
        Order order = (Order) repository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Recurso não encontrado")); // Busca o produto pelo ID no repositório, lançando uma exceção se não for encontrado
        return new OrderDTO(order);
    }
}
