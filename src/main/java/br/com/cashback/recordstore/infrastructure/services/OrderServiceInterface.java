package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderServiceInterface {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(long id);
    Page<OrderResponse> getOrdersByDateRange(LocalDate initialDate, LocalDate finalDate, Pageable pageable);
}
