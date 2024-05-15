package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(ProductDao productDao,int quantity,String username);
    void deleteCartById(Long id);
    ShoppingCart getCart(String username);
    ShoppingCart updateCart(ProductDao productDao,int quantity,String username);
    ShoppingCart removeItemToCart(ProductDao productDao,String username);

}
