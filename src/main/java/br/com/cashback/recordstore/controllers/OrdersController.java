package br.com.cashback.recordstore.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders() {
        return "getOrders";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String getOrder(@PathVariable int id) {
        return "getOrder: " + id;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrder() {
        return "createOrder";
    }
}
