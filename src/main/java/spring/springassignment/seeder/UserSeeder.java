package spring.springassignment.seeder;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springassignment.entity.User;
import spring.springassignment.entity.enums.ProductSimpleStatus;
import spring.springassignment.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserSeeder {
    @Autowired
    UserRepository userRepository;
    public static List<User> listUsers;

    public void userSeeder() {
        Faker faker = new Faker();
        listUsers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listUsers.add(User.builder()
                            .id(UUID.randomUUID().toString())
                            .fullName(faker.name().fullName())
                            .email(faker.name().title())
                            .phone(faker.phoneNumber().cellPhone())
                            .status(ProductSimpleStatus.ACTIVE)
                    .build());
        }
        userRepository.saveAll(listUsers);
    }

}
