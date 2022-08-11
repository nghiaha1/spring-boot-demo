package spring.springassignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.springassignment.entity.Account;
import spring.springassignment.entity.dto.AccountDTO;
import spring.springassignment.entity.dto.RegisterDTO;
import spring.springassignment.repository.AccountRepository;
import spring.springassignment.service.AccountService;

@RestController
@RequestMapping(path = "api/v1/accounts")
@CrossOrigin("*")
public class AccountApi {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, path = "login")
    public ResponseEntity<?> login(@RequestBody AccountDTO account) {
        Account exitingAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (exitingAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed!");
        }
        boolean result = passwordEncoder.matches(account.getPassword(), exitingAccount.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @RequestMapping(method = RequestMethod.POST, path = "register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        Account account = accountService.register(registerDTO);
        if (account == null) {
            return new ResponseEntity<>("server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
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
