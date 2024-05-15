package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Dao.CustomerDao;
import com.myecom.gallery.gallery.Model.Customer;

public interface CustomerService {

    Customer save(CustomerDao customerDao);
    Customer update(CustomerDao customerDao);
    Customer changePassword(CustomerDao customerDao);
    CustomerDao getCustomer(String username);
    Customer findByUsername(String username);
}
