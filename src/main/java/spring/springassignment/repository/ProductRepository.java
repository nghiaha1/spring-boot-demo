package spring.springassignment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.enums.ProductSimpleStatus;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByStatusEquals(ProductSimpleStatus status, Pageable pageable);

}
