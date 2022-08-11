package spring.springassignment.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationSeeder implements CommandLineRunner {
    @Autowired
    ProductSeeder productSeeder;
    @Autowired
    CategorySeeder categorySeeder;
    @Autowired
    OrderSeeder orderSeeder;
    @Autowired
    AccountSeeder accountSeeder;
    @Autowired
    UserSeeder userSeeder;
    private final boolean isCreated = true;


    @Override
    public void run(String... args) throws Exception {
        if (isCreated) {
            categorySeeder.categorySeeder();
            productSeeder.productSeeder();
            userSeeder.userSeeder();
            accountSeeder.accountSeeder();
            orderSeeder.orderSeeder();
            log.info("done");
        }
    }
}