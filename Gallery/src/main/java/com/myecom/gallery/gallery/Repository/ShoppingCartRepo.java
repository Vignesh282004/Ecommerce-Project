package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart,Long> {
}
