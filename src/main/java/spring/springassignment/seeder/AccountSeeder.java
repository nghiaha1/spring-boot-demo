package spring.springassignment.seeder;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springassignment.entity.Account;
import spring.springassignment.repository.AccountRepository;
import spring.springassignment.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountSeeder {
    @Autowired
    AccountRepository accountRepository;

    public static List<Account> accounts;
    public static final int NUMBER_OF_USER = 30;

    public void accountSeeder() {
        Faker faker = new Faker();
        accounts = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_USER; i++) {
            accounts.add(Account.builder()
                            .id(UUID.randomUUID().toString())
                            .fullName(faker.name().name())
                            .username(faker.name().username())
                            .password(faker.letterify("abc"))
                    .build());
        }
        accountRepository.saveAll(accounts);
    }
}
