package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Model.Order;
import com.myecom.gallery.gallery.Model.ShoppingCart;

import java.util.List;

public interface OrderService {

    Order save(ShoppingCart shoppingCart);
    Order acceptOrder(Long id);
    void cancaelOrder(Long id);
    List<Order> findALlOrders();
    List<Order> findAll(String username);
}
