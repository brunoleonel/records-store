package br.com.cashback.recordstore.controllers;

import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/records")
public class RecordController {

    @Autowired
    private RecordServiceInterface recordService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Record> getRecords(
        @RequestParam(name = "genre") Optional<String> genre,
        @RequestParam(name = "page") Optional<Integer> page,
        @RequestParam(name = "count") Optional<Integer> count
    ) {
        String genreSort = genre.orElse(null);
        Integer pageNumber = page.orElse(0);
        Integer recCount = count.orElse(10);
        Pageable pageable = PageRequest.of(pageNumber, recCount);
        return recordService.getRecords(genreSort, pageable);
    }

    @RequestMapping(path = "/{id}")
    public Record getRecord(@PathVariable long id) {
        return recordService.getRecordById(id);
    }
}
