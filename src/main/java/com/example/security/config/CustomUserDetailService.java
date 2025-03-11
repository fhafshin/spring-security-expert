package com.example.security.config;

import com.example.security.entity.Customer;
import com.example.security.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;
private final PasswordEncoder passwordEncoder;
    public CustomUserDetailService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;


        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Customer Not Found"));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }

    /**
     * @param user
     */
    public void createUser(Customer user) {

        userExists(user.getEmail());
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        customerRepository.save(user);

    }

    /**
     * @param user
     */
    public void updateUser(UserDetails user) {

    }

    /**
     * @param username
     */
    public void deleteUser(String username) {

    }

    /**
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    public void changePassword(String oldPassword, String newPassword) {

    }


    public boolean userExists(String email) {
        Optional<Customer> user = customerRepository.findByEmail(email);

        if (user.isPresent()) {
            return true;
        }
        return false;

    }
}
