package br.com.cashback.recordstore.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "records")
public class Record {

    @Id
    private String id;

    private String title;

    private float price;

    private String genre;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "record")
    private List<OrderRecord> orderRecords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<OrderRecord> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(List<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }
}
