package spring.springassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springassignment.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
