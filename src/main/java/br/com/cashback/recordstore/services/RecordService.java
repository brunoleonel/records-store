package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.RecordRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService implements RecordServiceInterface {

    @Autowired
    private RecordRepositoryInterface recordRepository;

    @Override
    public List<Record> getRecordsByIdIn(String ...ids) {
        return recordRepository.getRecordByIdIn(ids);
    }

    @Override
    public RecordResponse getRecordById(String id) {
        Optional<Record> result = recordRepository.findById(id);
        Record record = result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
        return new RecordResponse(record);
    }

    @Override
    public Page<RecordResponse> getRecords(String genre, Pageable pageable) {
        if (genre != null) {
            Page<Record> records = recordRepository.getRecordsByGenreOrderByTitle(genre, pageable);
            return records.map(record -> new RecordResponse(record));
        }

        Sort sort = Sort.by("title");
        Pageable genericPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Record> records = recordRepository.findAll(genericPageable);
        return records.map(record -> new RecordResponse(record));
    }

    @Override
    public void saveAll(List<Record> records) {
        recordRepository.saveAll(records);
    }

    @Override
    public void checkRecordsAbscence() {
        long count = recordRepository.count();
        if (count > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Records already synchronized");
        }
    }

    public void setRecordRepository(RecordRepositoryInterface recordRepository) {
        this.recordRepository = recordRepository;
    }
}
