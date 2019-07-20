package br.com.cashback.recordstore.infrastructure.repositories;

import br.com.cashback.recordstore.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepositoryInterface extends JpaRepository<Record, Long> {
}
