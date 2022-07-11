package spring.springassignment.seeder;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springassignment.entity.Category;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.enums.ProductSimpleStatus;
import spring.springassignment.repository.ProductRepsitory;
import spring.springassignment.util.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductSeeder {
    @Autowired
    ProductRepsitory productRepsitory;

    private final static int NUMBER_OF_PRODUCTS = 200;
    public static List<Product> products;

    public void ProductSeeder() {
        Faker faker = new Faker();
        Product product = new Product();
        products = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            int randomCategoryIndex = NumberUtil.getRandomNumber(0, CategorySeeder.categories.size() -1);
            Category category = CategorySeeder.categories.get(randomCategoryIndex);
            products.add(Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name(faker.name().name())
                            .description(faker.lorem().sentence(50))
                            .detail(faker.lorem().sentence(200))
                            .price(BigDecimal.valueOf(NumberUtil.getRandomNumber(100, 9999) * 1000))
                            .thumbnails(faker.avatar().image())
                            .categories(category)
                            .status(ProductSimpleStatus.ACTIVE)
                    .build());
        }
        productRepsitory.saveAll(products);
    }
}
