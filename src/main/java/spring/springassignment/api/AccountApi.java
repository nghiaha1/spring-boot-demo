package spring.springassignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.springassignment.entity.Account;
import spring.springassignment.repository.AccountRepository;

@RestController
@RequestMapping(path = "api/v1/accounts")
@CrossOrigin("*")
public class AccountApi {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST, path = "login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Account exitingAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (exitingAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed!");
        }
        boolean result = passwordEncoder.matches(account.getPasswordHash(), exitingAccount.getPasswordHash());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @RequestMapping(method = RequestMethod.POST, path = "register")
    public Account register(@RequestBody Account account) {
        account.setPasswordHash(passwordEncoder.encode(account.getPasswordHash()));
        accountRepository.save(account);
        return account;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Account>> findAll() {
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "users")
    public HttpStatus userLogin() {
        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET, path = "admins")
    public HttpStatus adminLogin() {
        return HttpStatus.OK;
    }
}
