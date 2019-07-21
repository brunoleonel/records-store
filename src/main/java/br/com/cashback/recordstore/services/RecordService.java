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
    public List<Record> getRecordsByIdIn(Long ...ids) {
        return recordRepository.getRecordByIdIn(ids);
    }

    @Override
    public RecordResponse getRecordById(long id) {
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
}
