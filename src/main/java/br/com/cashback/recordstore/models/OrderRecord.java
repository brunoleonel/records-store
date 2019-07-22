package br.com.cashback.recordstore.models;

import javax.persistence.*;

@Entity
@Table(name = "order_records")
public class OrderRecord {

    @EmbeddedId
    private OrderRecordId id;

    private float cashback;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id", insertable = false, updatable = false)
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    public OrderRecordId getId() {
        return id;
    }

    public void setId(OrderRecordId id) {
        this.id = id;
    }

    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
