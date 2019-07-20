package br.com.cashback.recordstore.resources.responses;

import br.com.cashback.recordstore.models.Record;

public class RecordResponse {

    private long id;
    private String title;
    private float price;
    private String genre;

    public RecordResponse(Record record) {
        this.id = record.getId();
        this.title = record.getTitle();
        this.price = record.getPrice();
        this.genre = record.getGenre();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }
}
