package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.models.Record;

public interface RecordServiceInterface {
    Record getRecordById(long id);
}
