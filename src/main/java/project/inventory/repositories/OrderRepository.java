package project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.inventory.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}