package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.repositories.CashbackIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CashbackIndexService implements CashbackIndexServiceInterface {

    @Autowired
    private CashbackIndexRepositoryInterface cashbackIndexRepository;

    @Override
    public float getCashBackIndexByGenreForDayOfWeek(String genre) {
        String day = this.getDayOfWeek();
        float cashbackIndex = this.cashbackIndexRepository.getCashbackIndexByGenreForDayOfWeek(genre, day);
        return cashbackIndex;
    }

    @Override
    public List<String> getGenres() {
        return cashbackIndexRepository.getGenres();
    }

    private String getDayOfWeek() {
        LocalDate now = LocalDate.now();
        String day = String.valueOf(now.getDayOfWeek());
        return day.toLowerCase();
    }

    public void setCashbackIndexRepository(CashbackIndexRepositoryInterface cashbackIndexRepository) {
        this.cashbackIndexRepository = cashbackIndexRepository;
    }
}
