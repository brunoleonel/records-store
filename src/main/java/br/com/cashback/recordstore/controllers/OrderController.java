package br.com.cashback.recordstore.controllers;

import br.com.cashback.recordstore.infrastructure.services.OrderServiceInterface;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceInterface orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders() {
        return "getOrders";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String getOrder(@PathVariable int id) {
        return "getOrder: " + id;
    }

    @RequestMapping(method = RequestMethod.POST)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return this.orderService.createOrder(orderRequest);
    }
}
