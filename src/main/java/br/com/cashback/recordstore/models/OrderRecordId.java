package br.com.cashback.recordstore.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderRecordId implements Serializable {

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "record_id")
    private String recordId;

    public OrderRecordId(long orderId, String recordId) {
        this.orderId = orderId;
        this.recordId = recordId;
    }

    public OrderRecordId(){}

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
