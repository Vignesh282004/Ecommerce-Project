package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Dao.AdminDao;
import com.myecom.gallery.gallery.Model.Admin;
import com.myecom.gallery.gallery.Repository.AdminRepo;
import com.myecom.gallery.gallery.Repository.RoleRepo;
import com.myecom.gallery.gallery.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public Admin save(AdminDao adminDao) {
        Admin admin = new Admin();
        admin.setFirstname(adminDao.getFirstname());
        admin.setLastname(adminDao.getLastname());
        admin.setUsername(adminDao.getUsername());
        admin.setPassword(adminDao.getPassword());
        admin.setRoles(Arrays.asList(roleRepo.findByName("ADMIN")));
        return adminRepo.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
      return adminRepo.findByUsername(username);
    }
}
