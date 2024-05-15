package com.myecom.gallery.gallery.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "customers",uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name",referencedColumnName = "id")
    private City city;
    private String country;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "customers_roles",joinColumns = @JoinColumn(name = "customer_id",referencedColumnName = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Collection<Role> roles;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orders;

    public Customer() {
        this.country = "INDIA";
        this.shoppingCart = new ShoppingCart();
        this.orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city=" + city +
                ", country='" + country + '\'' +
                ", roles=" + roles +
                ", shoppingCart=" + shoppingCart +
                ", orders=" + orders +
                '}';
    }
}
