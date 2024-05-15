package com.myecom.gallery.gallery.Dao;

import com.myecom.gallery.gallery.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDao {

    private Long id;
    private ShoppingCartDao shoppingCartDao;
    private ProductDao productDao;
    private double unitPrice;
    private int quantity;
}
