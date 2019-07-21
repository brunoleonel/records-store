package br.com.cashback.recordstore.resources.responses;

import java.time.LocalDate;
import java.util.List;

public class OrderResponse {

    private long id;

    private List<RecordResponse> records;

    private float cashback;

    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<RecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<RecordResponse> records) {
        this.records = records;
    }

    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}