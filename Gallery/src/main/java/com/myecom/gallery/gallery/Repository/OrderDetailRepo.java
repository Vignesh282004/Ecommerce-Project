package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OrdersList,Long> {
    @Query("select o from Order o  where o.customer.id = ?1")
    List<OrdersList> findAllByCustomerId(Long id);
}
