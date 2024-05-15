package com.myecom.gallery.gallery.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinColumn(name = "shopping_cart_id",referencedColumnName = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;

    private int quantity;
    private double unitPrice;
    private double totalPrice;
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", shoppingCart=" + shoppingCart.getId() +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
