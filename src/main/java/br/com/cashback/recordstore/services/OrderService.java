package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.OrderServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private RecordServiceInterface recordService;

    @Autowired
    private CashbackIndexServiceInterface cashbackIndexService;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Record record = this.recordService.getRecordById(orderRequest.getRecordId());

        float cashbackIndex = this.cashbackIndexService.getCashBackIndexByGenreForDayOfWeek(record.getGenre());
        float recordCashback = record.getPrice() * cashbackIndex;

        RecordResponse recResponse = new RecordResponse(record);
        OrderResponse response = new OrderResponse();
        response.setRecord(recResponse);
        response.setCashback(recordCashback);
        response.setId(1);

        return response;
    }
}
