package br.com.cashback.recordstore.controllers;

import br.com.cashback.recordstore.infrastructure.services.OrderServiceInterface;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceInterface orderService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<OrderResponse> getOrders(
        @RequestParam(name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> from,
        @RequestParam(name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> to,
        @RequestParam(name = "page") Optional<Integer> page,
        @RequestParam(name = "count") Optional<Integer> count
    ) {
        LocalDate today = LocalDate.now();
        LocalDate initialDate = from.orElse(today.minusMonths(1));
        LocalDate finalDate = to.orElse(initialDate.plusMonths(1));
        Pageable pageable = PageRequest.of(page.orElse(0), count.orElse(10));
        return orderService.getOrdersByDateRange(initialDate, finalDate, pageable);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public OrderResponse getOrder(@PathVariable long id) {
        return orderService.getOrderById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return this.orderService.createOrder(orderRequest);
    }
}
