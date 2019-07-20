package br.com.cashback.recordstore.resources.requests;

import javax.validation.Valid;
import java.util.List;

public class OrderRequest {

    @Valid
    private List<OrderItemRequest> records;

    public List<OrderItemRequest> getRecords() {
        return records;
    }
}
