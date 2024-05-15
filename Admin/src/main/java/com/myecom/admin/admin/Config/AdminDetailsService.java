package com.myecom.admin.admin.Config;

import com.myecom.gallery.gallery.Model.Admin;
import com.myecom.gallery.gallery.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class AdminDetailsService implements UserDetailsService
{
    @Autowired
    private AdminRepo adminRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUsername(username);
        if(admin == null) {
            throw new UsernameNotFoundException("UserName Not found ....");
        }
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                admin.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
