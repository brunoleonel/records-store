package br.com.cashback.recordstore.unit.services;

import br.com.cashback.recordstore.infrastructure.repositories.OrderRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Order;
import br.com.cashback.recordstore.models.OrderRecord;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.requests.OrderItemRequest;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import br.com.cashback.recordstore.services.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Mock
    private RecordServiceInterface recordService;

    @Mock
    private CashbackIndexServiceInterface cashbackIndexService;

    @Mock
    private OrderRepositoryInterface orderRepository;

    @Test
    public void testShouldCreateOrder() {

        OrderItemRequest item = new OrderItemRequest();
        item.setRecordId("abcde");

        List<OrderItemRequest> orderItemRequestList = Arrays.asList(item);
        OrderRequest request = new OrderRequest();
        request.setRecords(orderItemRequestList);

        Record record = new Record();
        record.setId("abcde");
        when(recordService.getRecordsByIdIn(any())).thenReturn(Arrays.asList(record));

        when(cashbackIndexService.getCashBackIndexByGenreForDayOfWeek(anyString())).thenReturn(1.5f);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        OrderService orderService = new OrderService();
        orderService.setDependencies(recordService, cashbackIndexService, orderRepository);
        orderService.createOrder(request);
    }

    @Test
    public void testShouldGetOrderById() {

        Order order = new Order();
        order.setId(1l);
        order.setDate(LocalDate.now());
        order.setCashback(1.5f);

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrder(order);
        orderRecord.setRecord(new Record());

        order.setOrderRecords(Arrays.asList(orderRecord));

        when(orderRepository.findById(1l)).thenReturn(Optional.of(order));

        OrderService orderService = new OrderService();
        orderService.setDependencies(recordService, cashbackIndexService, orderRepository);
        OrderResponse result = orderService.getOrderById(1l);

        assertThat(result.getId()).isEqualTo(1);
    }

    @Test(expected = ResponseStatusException.class)
    public void testShouldntGetOrderById() {

        when(orderRepository.findById(1l)).thenReturn(Optional.empty());

        OrderService orderService = new OrderService();
        orderService.setDependencies(recordService, cashbackIndexService, orderRepository);
        orderService.getOrderById(1l);
    }

    @Test
    public void testShouldGetOrdersByDateRange() {

        Order order = new Order();
        order.setCashback(1.5f);
        order.setId(2);

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrder(order);
        orderRecord.setRecord(new Record());

        order.setOrderRecords(Arrays.asList(orderRecord));

        List<Order> orders = Arrays.asList(order);

        Page<Order> page = new PageImpl<>(orders);

        when(orderRepository.findAllOrderByDateBetweenOrderByDateDesc(
                any(LocalDate.class),
                any(LocalDate.class),
                any(Pageable.class))).thenReturn(page);

        OrderService orderService = new OrderService();
        orderService.setDependencies(recordService, cashbackIndexService, orderRepository);
        Page<OrderResponse> result = orderService.getOrdersByDateRange(
                LocalDate.MIN,
                LocalDate.MAX,
                PageRequest.of(0,1)
        );

        assertThat(result.getContent().get(0).getId()).isEqualTo(2);
    }
}
