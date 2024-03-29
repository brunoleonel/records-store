package br.com.cashback.recordstore.infrastructure.services;

import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordServiceInterface {
    List<Record> getRecordsByIdIn(String ...ids);
    RecordResponse getRecordById(String id);
    Page<RecordResponse> getRecords(String genre, Pageable pageable);
    void saveAll(List<Record> records);
    void checkRecordsAbscence();
}
