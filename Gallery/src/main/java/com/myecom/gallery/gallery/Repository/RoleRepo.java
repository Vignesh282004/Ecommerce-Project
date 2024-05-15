package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
