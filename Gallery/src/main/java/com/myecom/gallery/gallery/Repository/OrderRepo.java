package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
