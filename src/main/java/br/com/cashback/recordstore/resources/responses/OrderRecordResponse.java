package br.com.cashback.recordstore.resources.responses;

import br.com.cashback.recordstore.models.Record;

public class OrderRecordResponse {

    private RecordResponse record;
    private float cashback;

    public OrderRecordResponse(Record record, float cashback) {
        this.record = new RecordResponse(record);
        this.cashback = cashback;
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
