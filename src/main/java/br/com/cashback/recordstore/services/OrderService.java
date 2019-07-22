package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.OrderRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.OrderServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Order;
import br.com.cashback.recordstore.models.OrderRecord;
import br.com.cashback.recordstore.models.OrderRecordId;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.requests.OrderItemRequest;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderRecordResponse;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private RecordServiceInterface recordService;

    @Autowired
    private CashbackIndexServiceInterface cashbackIndexService;

    @Autowired
    private OrderRepositoryInterface orderRepository;

    private Map<String, Float> cashbackIndexes;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        List<Record> records = getOrderRecords(orderRequest.getRecords());

        this.cashbackIndexes = getCashbackIndexes(records);
        float totalCashback = getTotalCashback(records);

        Order order = new Order();
        order.setCashback(totalCashback);
        order.setDate(LocalDate.now());
        this.orderRepository.save(order);

        saveOrderRecords(order, records);

        OrderResponse response = prepareResponse(totalCashback, order);

        return response;
    }

    private Float getTotalCashback(List<Record> records) {
        return records.stream()
            .map(record -> {
                float cashbackIndex = this.cashbackIndexes.get(record.getGenre());
                float recordCashback = record.getPrice() * cashbackIndex;
                return recordCashback;
            })
            .reduce(0f, Float::sum);
    }

    private List<Record> getOrderRecords(List<OrderItemRequest> orderItems) {
        List<String> recordIds = orderItems
                .stream()
                .map(item -> item.getRecordId())
                .collect(Collectors.toList());

        String[] ids = recordIds.toArray(new String[recordIds.size()]);
        List<Record> records = this.recordService.getRecordsByIdIn(ids);
        return records;
    }

    private OrderResponse prepareResponse(float totalCashback, Order order) {
        List<OrderRecordResponse> records = order.getOrderRecords().stream()
                .map(oRec -> new OrderRecordResponse(oRec.getRecord(), oRec.getCashback()))
                .collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setRecords(records);
        response.setCashback(totalCashback);
        response.setDate(order.getDate());
        response.setId(order.getId());
        return response;
    }

    @Override
    public OrderResponse getOrderById(long id) {
        Optional<Order> result = orderRepository.findById(id);
        Order order = result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return prepareResponse(order.getCashback(), order);
    }

    @Override
    public Page<OrderResponse> getOrdersByDateRange(LocalDate initialDate, LocalDate finalDate, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllOrderByDateBetweenOrderByDateDesc(initialDate, finalDate, pageable);
        Page<OrderResponse> orderResponsePage = orders.map(order -> prepareResponse(order.getCashback(), order));
        return orderResponsePage;
    }

    private Map<String, Float> getCashbackIndexes(List<Record> records) {
        return records.stream()
            .collect(
                Collectors.toMap(
                    rec -> rec.getGenre(),
                    rec -> cashbackIndexService.getCashBackIndexByGenreForDayOfWeek(rec.getGenre())
                )
            );
    }

    private void saveOrderRecords(Order order, List<Record> records) {
        List<OrderRecord> orderRecords = records.stream()
                .map(record -> {
                    OrderRecord ord = new OrderRecord();
                    ord.setCashback(record.getPrice() * this.cashbackIndexes.get(record.getGenre()));
                    ord.setId(new OrderRecordId(order.getId(), record.getId()));
                    ord.setRecord(record);
                    ord.setOrder(order);
                    return ord;
                })
                .collect(Collectors.toList());

        order.setOrderRecords(orderRecords);

        this.orderRepository.save(order);
    }
}
