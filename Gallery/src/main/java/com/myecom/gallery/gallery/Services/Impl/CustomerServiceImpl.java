package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Dao.CustomerDao;
import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Repository.CustomerRepo;
import com.myecom.gallery.gallery.Repository.RoleRepo;
import com.myecom.gallery.gallery.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final RoleRepo roleRepo;

    @Override
    public Customer save(CustomerDao customerDao) {
        Customer customer = new Customer();
        customer.setFirstName(customerDao.getFirstName());
        customer.setLastName(customerDao.getLastName());
        customer.setUsername((customerDao.getUsername()));
        customer.setPassword(customerDao.getPassword());
        customer.setRoles(Arrays.asList(roleRepo.findByName("CUSTOMER")));
        return customerRepo.save(customer);
    }

    @Override
    public Customer update(CustomerDao customerDao) {
        Customer customer = customerRepo.findByUsername(customerDao.getUsername());
        customer.setAddress(customerDao.getAddress());
        customer.setCity(customerDao.getCity());
        customer.setCountry(customerDao.getCountry());
        customer.setPhoneNumber(customerDao.getPhoneNumber());
      return   customerRepo.save(customer);
    }

    @Override
    public Customer changePassword(CustomerDao customerDao) {
        Customer customer = customerRepo.findByUsername(customerDao.getUsername());
        customer.setPassword(customerDao.getPassword());
        return customerRepo.save(customer);
    }

    @Override
    public CustomerDao getCustomer(String username) {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerRepo.findByUsername(username);
        customerDao.setFirstName(customer.getFirstName());
        customerDao.setLastName(customer.getLastName());
        customerDao.setUsername(customer.getUsername());
        customerDao.setPassword(customer.getPassword());
        customerDao.setAddress(customer.getAddress());
        customerDao.setPhoneNumber(customer.getPhoneNumber());
        customerDao.setCity(customer.getCity());
        customerDao.setCountry(customer.getCountry());
        return customerDao;
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }
}
