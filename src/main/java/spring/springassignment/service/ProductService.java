package spring.springassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.enums.ProductSimpleStatus;
import spring.springassignment.repository.ProductRepsitory;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepsitory productRepsitory;

    public Iterable<Product> findAll() {
        Iterable<Product> products = productRepsitory.findAll();
        return products;
    }

    public Iterable<Product> findAllWithPagnation() {
        Page<Product> products = productRepsitory.findAllByStatusEquals(ProductSimpleStatus.ACTIVE, PageRequest.of(0, 10));
        return products;
    }

    public Product update(Product obj) {
        return productRepsitory.save(obj);
    }

    public Optional<Product> findById(String id) {
        return productRepsitory.findById(id);
    }

    public void deleteById(String id) {
        productRepsitory.deleteById(id);
    }
}
