package com.myecom.gallery.gallery.Dao;

import com.myecom.gallery.gallery.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDao {

    private Long id;
    private Customer customer;
    private double totalPrice;
    private int totalItems;
    private Set<CartItemDao> cartItemDaoSet;
}
