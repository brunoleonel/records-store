package br.com.cashback.recordstore.infrastructure.repositories;

import br.com.cashback.recordstore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryInterface extends JpaRepository<Order, Long> {
}
