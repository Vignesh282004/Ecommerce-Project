package com.myecom.customer.customer.Configs;

import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class CustomerDetailService  implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByUsername(username);
        if(customer == null) {
            throw  new UsernameNotFoundException("User Not Found....");
        }
        return new User(customer.getUsername(),
                customer.getPassword(),
                customer.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
