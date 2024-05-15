package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {

    Admin findByUsername(String username);
}
