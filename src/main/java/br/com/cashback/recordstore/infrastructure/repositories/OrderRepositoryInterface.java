package br.com.cashback.recordstore.infrastructure.repositories;

import br.com.cashback.recordstore.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrderRepositoryInterface extends JpaRepository<Order, Long> {
    Page<Order> findAllOrderByDateBetweenOrderByDateDesc(LocalDate initialDate, LocalDate finalDate, Pageable pageable);
}
