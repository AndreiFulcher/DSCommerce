package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourseNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true) // Serve para definir o escopo da transação como somente leitura
    public OrderDTO findById(Long id) {
        Order order = (Order) repository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Recurso não encontrado")); // Busca o produto pelo ID no repositório, lançando uma exceção se não for encontrado
        authService.validateSelfOrAdmin(order.getClient().getId()); // Verifica se o usuário autenticado é o cliente do pedido ou um administrador
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        User user = userService.authenticate(); // Obtém o usuário autenticado
        order.setClient(user);
        for(OrderItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());// Obtém uma referência ao produto usando o ID do item
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        repository.save(order); // Salva o pedido no banco de dados, gerando um ID para ele
        orderItemRepository.saveAll(order.getItems()); // Salva os itens do pedido no banco de dados, associando-os ao pedido
        return new OrderDTO(order);
    }
}
