package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    private String getDayOfWeek() {
        LocalDate now = LocalDate.now();
        String day = String.valueOf(now.getDayOfWeek());
        return day.toLowerCase();
    }
}
