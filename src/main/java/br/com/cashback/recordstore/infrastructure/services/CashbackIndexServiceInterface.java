package br.com.cashback.recordstore.infrastructure.services;

import java.util.List;

public interface CashbackIndexServiceInterface {
    float getCashBackIndexByGenreForDayOfWeek(String genre);
    List<String> getGenres();
}
