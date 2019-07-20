package br.com.cashback.recordstore.infrastructure.repositories;

public interface CashbackIndexRepositoryInterface {
    float getCashbackIndexByGenreForDayOfWeek(String genre, String day);
}
