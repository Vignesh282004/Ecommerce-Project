package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Model.*;
import com.myecom.gallery.gallery.Repository.CustomerRepo;
import com.myecom.gallery.gallery.Repository.OrderDetailRepo;
import com.myecom.gallery.gallery.Repository.OrderRepo;
import com.myecom.gallery.gallery.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService  {
    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final OrderDetailRepo orderDetailRepo;
    private  final ShoppingCartServiceImpl shoppingCartService;

    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart) {
       Order order = new Order();
       order.setOrderDate(new Date());
       order.setCustomer(shoppingCart.getCustomer());
       order.setPaymentMethod("cash");
       order.setAccept(false);
       order.setTax(2);
       order.setTotalPrice(shoppingCart.getTotalPrice());
       order.setQuantity(shoppingCart.getTotalItems());
       order.setOrderStatus("Pending");
       List<OrdersList> ordersdDetailLists = new ArrayList<>();
       for(CartItem item : shoppingCart.getCartItems())
       {
           OrdersList ordersList = new OrdersList();
           ordersList.setOrder(order);
           ordersList.setProduct(item.getProduct());
           orderDetailRepo.save(ordersList);
           ordersdDetailLists.add(ordersList);
       }
       order.setOrdersLists(ordersdDetailLists);
       shoppingCartService.deleteCartById(shoppingCart.getId());
       return orderRepo.save(order);
    }

    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepo.findById(id).get();
        order.setOrderStatus("Order Accepted");
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        return orderRepo.save(order);
    }

    @Override
    public void cancaelOrder(Long id) {
         orderRepo.deleteById(id);
    }

    @Override
    public List<Order> findALlOrders() {
        return orderRepo.findAll();
    }

    @Override
    public List<Order> findAll(String username) {
        Customer customer = customerRepo.findByUsername(username);
        List<Order> orders = customer.getOrders();
        return orders;
    }
}
