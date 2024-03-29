package br.com.cashback.recordstore.infrastructure.repositories;

import br.com.cashback.recordstore.models.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepositoryInterface extends JpaRepository<Record, String> {
    List<Record> getRecordByIdIn(String ...ids);
    Page<Record> getRecordsByGenreOrderByTitle(String genre, Pageable pageable);
}
