package br.com.cashback.recordstore.infrastructure.repositories;

import br.com.cashback.recordstore.models.CashbackIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CashbackIndexRepositoryInterface {
    float getCashbackIndexByGenreForDayOfWeek(String genre, String day);
    List<String> getGenres();
}
