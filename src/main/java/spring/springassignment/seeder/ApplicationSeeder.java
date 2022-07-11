package spring.springassignment.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    @Autowired
    ProductSeeder productSeeder;
    @Autowired
    CategorySeeder categorySeeder;
    @Autowired
    OrderSeeder orderSeeder;
    @Autowired
    AccountSeeder accountSeeder;

    private boolean isCreated = false;

    @Override
    public void run(String... args) throws Exception {
        if (isCreated) {
            categorySeeder.CategorySeeder();
            productSeeder.ProductSeeder();
            accountSeeder.accountSeeder();
            orderSeeder.orderSeeder();
        }
    }
}
