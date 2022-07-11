package spring.springassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springassignment.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
