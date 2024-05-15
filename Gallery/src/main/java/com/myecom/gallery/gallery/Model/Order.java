package com.myecom.gallery.gallery.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Date DeliveryDate;
    private Date OrderDate;
    private String orderStatus;
    private String paymentMethod;
    private double totalPrice;
    private double tax;
    private int quantity;
    private boolean isAccept;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrdersList>ordersLists;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", DeliveryDate=" + DeliveryDate +
                ", OrderDate=" + OrderDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", totalPrice=" + totalPrice +
                ", tax=" + tax +
                ", quantity=" + quantity +
                ", customer=" + customer.getUsername() +
                ", ordersLists=" + ordersLists.size() +
                '}';
    }
}
