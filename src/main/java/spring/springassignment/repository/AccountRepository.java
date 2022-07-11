package spring.springassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springassignment.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUsername(String username);
}
