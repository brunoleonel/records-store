package br.com.cashback.recordstore.resources.responses;

public class OrderResponse {

    private long id;

    private RecordResponse record;

    private float cashback;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RecordResponse getRecord() {
        return record;
    }

    public void setRecord(RecordResponse record) {
        this.record = record;
    }

    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }
}
