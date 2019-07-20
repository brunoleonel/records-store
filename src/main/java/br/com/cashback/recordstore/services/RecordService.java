package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.RecordRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService implements RecordServiceInterface {

    @Autowired
    private RecordRepositoryInterface recordRepository;

    @Override
    public List<Record> getRecordsByIdIn(Long ...ids) {
         return this.recordRepository.getRecordByIdIn(ids);
    }
}
