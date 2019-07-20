package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.models.Record;

import java.util.List;

public interface RecordServiceInterface {
    List<Record> getRecordsByIdIn(Long ...ids);
}
