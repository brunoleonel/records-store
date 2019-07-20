package br.com.cashback.recordstore.models;

import javax.persistence.*;

@Entity
@Table(name = "cashback_index")
public class CashbackIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10)
    private String genre;

    @Column(scale = 2, precision = 2)
    private float sunday;

    @Column(scale = 2, precision = 2)
    private float monday;

    @Column(scale = 2, precision = 2)
    private float tuesday;

    @Column(scale = 2, precision = 2)
    private float wednesday;

    @Column(scale = 2, precision = 2)
    private float thursday;

    @Column(scale = 2, precision = 2)
    private float friday;

    @Column(scale = 2, precision = 2)
    private float saturday;
}
