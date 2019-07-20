package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;

public interface OrderServiceInterface {
    OrderResponse createOrder(OrderRequest orderRequest);
}
