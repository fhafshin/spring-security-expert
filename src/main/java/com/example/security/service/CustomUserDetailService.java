package com.example.security.service;

import com.example.security.entity.Customer;
import com.example.security.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService, UserDetailsManager {

    private final CustomerRepository customerRepository;

    public CustomUserDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer= customerRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Customer Not Found"));


       List< GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
       return new User(customer.getEmail(),customer.getPwd(),authorities);
    }

    /**
     * @param user
     */
    @Override
    public void createUser(UserDetails user) {

    }

    /**
     * @param user
     */
    @Override
    public void updateUser(UserDetails user) {

    }

    /**
     * @param username
     */
    @Override
    public void deleteUser(String username) {

    }

    /**
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    /**
     * @param username
     * @return
     */
    @Override
    public boolean userExists(String username) {
        return false;
    }
}
