package spring.springassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.springassignment.entity.Account;
import spring.springassignment.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

/*
    tìm account theo username
 */
@Service
@Transactional
public class MyAccountDetailService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //tìm account theo username trong bảng accounts
        Account account = accountRepository.findAccountByUsername(username);
        /*
            tạo danh sách role
            có thể tạo ra bằng quyền riêng mapping n-n với table accounts
         */
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (account.getRole() == 1) {
            authorities.add(new SimpleGrantedAuthority("user")); //add new role in user
        }else if (account.getRole() == 2) {
            authorities.add(new SimpleGrantedAuthority("admin"));
        }else {
            authorities.add(new SimpleGrantedAuthority("guest"));
        }
        /*
            tạo đối tượng user detail theo thông tin username, password, quyền đc lấy ở trên.
            trong đó password là password đã đc mã hóa
         */
        return new User(account.getUsername(), account.getPasswordHash(), authorities);
    }
}
