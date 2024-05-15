package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.CartItem;
import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Model.Product;
import com.myecom.gallery.gallery.Model.ShoppingCart;
import com.myecom.gallery.gallery.Repository.CartItemRepo;
import com.myecom.gallery.gallery.Repository.ShoppingCartRepo;
import com.myecom.gallery.gallery.Services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepo shoppingCartRepo;
    private final CartItemRepo cartItemRepo;
    private final CustomerServiceImpl customerService;

    private int totalItem(Set<CartItem> cartItems) {
        int items =0;
        for(CartItem cartItem : cartItems) {
            items += cartItem.getQuantity();
        }
        return items;
    }

    private double totalPrice(Set<CartItem> cartItemList) {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getUnitPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    @Override
    public ShoppingCart addItemToCart(ProductDao productDao, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        if(cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItemsList = cart.getCartItems();
        CartItem cartItem = find(cartItemsList, productDao.getId());
        Product product = transfer(productDao);

        double unitPrice = productDao.getCostPrice();
        int itemquantity = 0;

        if(cartItemsList == null) {
            cartItemsList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setShoppingCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                //cartItem.setTotalPrice();
                cartItem.setShoppingCart(cart);
                cartItemsList.add(cartItem);
                cartItemRepo.save(cartItem);
            } else {
                itemquantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemquantity);
                cartItemRepo.save(cartItem);
            }
        }
        //imporrtaat upladod
        else {
            if(cartItem == null)
            {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setShoppingCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setShoppingCart(cart);
                cartItemsList.add(cartItem);
                cartItemRepo.save(cartItem);
            }
            else
            {
                itemquantity = cartItem.getQuantity() * quantity;
                cartItem.setQuantity(itemquantity);
                cartItemRepo.save(cartItem);
            }
        }
            cart.setCartItems(cartItemsList);

            double totalPrice = totalPrice(cart.getCartItems());
            int totalItem = totalItem(cart.getCartItems());

            cart.setTotalItems(totalItem);
            cart.setTotalPrice(totalPrice);
            cart.setCustomer(customer);
            return shoppingCartRepo.save(cart);

    }

    @Override
    public void deleteCartById(Long id) {
        ShoppingCart cart = shoppingCartRepo.getById(id);
        if(!ObjectUtils.isEmpty(cart) && !ObjectUtils.isEmpty(cart.getCartItems()))
        {
            //cartItemRepo.deleteById(id);
            cartItemRepo.deleteAll(cart.getCartItems());
        }
        cart.getCartItems().clear();
        cart.setTotalItems(0);
        cart.setTotalPrice(0);
        shoppingCartRepo.save(cart);
    }

    @Override
    public ShoppingCart getCart(String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        return cart;
    }

    @Override
    @Transactional
    public ShoppingCart updateCart(ProductDao productDao, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItems, productDao.getId());
        int itemquantity = quantity;

        cartItem.setQuantity(itemquantity);
        cartItemRepo.save(cartItem);
        shoppingCart.setCartItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        int totalItems = totalItem(cartItems);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItems);
        return shoppingCartRepo.save(shoppingCart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingCart removeItemToCart(ProductDao productDao, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, productDao.getId());
        cartItemList.remove(cartItem);
        cartItemRepo.delete(cartItem);
        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        return shoppingCartRepo.save(shoppingCart);
    }

    private Product transfer(ProductDao productDao)
    {
        Product product = new Product();
        product.setId(productDao.getId());
        product.setName(productDao.getName());
        product.setDescription(productDao.getDescription());
        product.setCurrentQuantity(productDao.getCurrentQuantity());
        product.setCostPrice(productDao.getCostPrice());
        product.setSalePrice(productDao.getSalePrice());
        product.setImage(productDao.getImage());
        product.setCategory(productDao.getCategory());
        product.set_activated(productDao.isActivated());
        product.set_deleted(productDao.isDeleted());
        return product;
    }

    private CartItem find(Set<CartItem> cartItems,long productId)
    {
        if(cartItems == null) return null;
        CartItem cartItem = null;
        for(CartItem cartItem1 : cartItems)
        {
                if(cartItem1.getProduct().getId() == productId) {
                    cartItem = cartItem1;
                }
        }
        return cartItem;
    }
}
