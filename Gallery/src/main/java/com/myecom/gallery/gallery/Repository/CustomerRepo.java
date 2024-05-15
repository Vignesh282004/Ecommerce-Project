package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByUsername(String username);
}
