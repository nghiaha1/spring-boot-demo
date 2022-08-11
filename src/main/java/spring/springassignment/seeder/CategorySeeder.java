package spring.springassignment.seeder;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springassignment.entity.Category;
import spring.springassignment.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategorySeeder {
    @Autowired
    CategoryRepository categoryRepository;
    
    private static final int NUMBER_OF_CATEGORIES = 50;
    public static List<Category> categories;
    
    public void categorySeeder() {
        Faker faker = new Faker();
        Category category = new Category();
        categories = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            categories.add(Category.builder()
                            .id(UUID.randomUUID().toString())
                            .name(faker.name().name())
                            .description(faker.lorem().sentence(50))
                            .detail(faker.lorem().sentence(200))
                    .build());
        }
        categoryRepository.saveAll(categories);
    }
}
