package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Dao.AdminDao;
import com.myecom.gallery.gallery.Model.Admin;

public interface AdminService {
    Admin save(AdminDao adminDao);
    Admin findByUsername(String username);
}
