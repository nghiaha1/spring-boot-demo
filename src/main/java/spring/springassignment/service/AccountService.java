package spring.springassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.springassignment.entity.Account;
import spring.springassignment.entity.dto.AccountDTO;
import spring.springassignment.entity.dto.LoginDTO;
import spring.springassignment.entity.dto.RegisterDTO;
import spring.springassignment.repository.AccountRepository;

import javax.transaction.Transactional;

@Transactional
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Account register(RegisterDTO registerDTO) {
        Account account = Account.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .status(1)
                .roles(registerDTO.getRoles())
                .build();
        return accountRepository.save(account);
    }

    public Account login(LoginDTO loginDTO) {
        return null;
    }
}
