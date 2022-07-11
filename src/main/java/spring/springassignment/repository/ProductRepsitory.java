package spring.springassignment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.enums.ProductSimpleStatus;

public interface ProductRepsitory extends JpaRepository<Product, String> {
    Page<Product> findAllByStatusEquals(ProductSimpleStatus status, Pageable pageable);

}
