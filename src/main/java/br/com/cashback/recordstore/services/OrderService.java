package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.OrderRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.OrderServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Order;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.requests.OrderItemRequest;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private RecordServiceInterface recordService;

    @Autowired
    private CashbackIndexServiceInterface cashbackIndexService;

    @Autowired
    private OrderRepositoryInterface orderRepository;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        List<Record> records = getOrderRecords(orderRequest.getRecords());
        float totalCashback = getTotalCashback(records);

        Order order = new Order();
        order.setCashback(totalCashback);
        order.setRecords(records);

        this.orderRepository.save(order);

        OrderResponse response = prepareResponse(totalCashback, order);

        return response;
    }

    private Float getTotalCashback(List<Record> records) {
        return records.stream()
            .map(record -> {
                float cashbackIndex = this.cashbackIndexService.getCashBackIndexByGenreForDayOfWeek(record.getGenre());
                float recordCashback = record.getPrice() * cashbackIndex;
                return recordCashback;
            })
            .reduce(0f, Float::sum);
    }

    private List<Record> getOrderRecords(List<OrderItemRequest> orderItems) {
        List<Long> recordIds = orderItems
                .stream()
                .map(item -> item.getRecordId())
                .collect(Collectors.toList());

        Long[] ids = recordIds.toArray(new Long[recordIds.size()]);
        List<Record> records = this.recordService.getRecordsByIdIn(ids);
        return records;
    }

    private OrderResponse prepareResponse(float totalCashback, Order order) {
        List<Record> records = order.getRecords();
        List<RecordResponse> recResponses = records.stream()
                .map(record -> new RecordResponse(record))
                .collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setRecords(recResponses);
        response.setCashback(totalCashback);
        response.setId(order.getId());
        return response;
    }
}
