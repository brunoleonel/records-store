package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.models.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordServiceInterface {
    List<Record> getRecordsByIdIn(Long ...ids);
    Record getRecordById(long id);
    Page<Record> getRecords(String genre, Pageable pageable);
}
