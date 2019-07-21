package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.RecordRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
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
         return this.recordRepository.getRecordByIdIn(ids);
    }

    @Override
    public Record getRecordById(long id) {
        Optional<Record> result = recordRepository.findById(id);
        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
    }

    @Override
    public Page<Record> getRecords(String genre, Pageable pageable) {
        if (genre != null) {
            return recordRepository.getRecordsByGenreOrderByTitle(genre, pageable);
        }

        Sort sort = Sort.by("title");
        Pageable genericPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return recordRepository.findAll(genericPageable);
    }
}
