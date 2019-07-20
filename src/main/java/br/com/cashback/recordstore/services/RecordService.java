package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.RecordRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService implements RecordServiceInterface {

    @Autowired
    private RecordRepositoryInterface recordRepository;

    @Override
    public Record getRecordById(long id) {
         return this.recordRepository.getOne(id);
    }
}
