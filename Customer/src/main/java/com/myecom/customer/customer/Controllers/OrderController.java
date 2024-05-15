package com.myecom.customer.customer.Controllers;

import com.myecom.gallery.gallery.Dao.CustomerDao;
import com.myecom.gallery.gallery.Model.*;
import com.myecom.gallery.gallery.Repository.CountryRepo;
import com.myecom.gallery.gallery.Services.Impl.CityServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.CountryServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.CustomerServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final CustomerServiceImpl customerServiceImpl;
    private final CityServiceImpl cityServiceImpl;
    private final CountryServiceImpl countryServiceImpl;
    private final CountryRepo countryRepo;

    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String acceptOrder(Model model, Principal principal, HttpSession session) {
        if (principal == null) return "redirect:/custlogin";
        else {
            Customer customer = customerServiceImpl.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            Order order = orderService.save(cart);
            session.removeAttribute("totalItems");
            model.addAttribute("order", order);
            model.addAttribute("success", "Order Added ....");
            return "order-detail";
        }
    }

    @RequestMapping(value = "/delete-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteorder(@PathVariable("id") Long id, RedirectAttributes attributes) {
        orderService.cancaelOrder(id);
        attributes.addFlashAttribute("success", "Order Canceled ....");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/custlogin";
        } else {
            Customer customer = customerServiceImpl.findByUsername(principal.getName());
            List<Order> orderList = customer.getOrders();
            model.addAttribute("address",customer.getAddress());
            model.addAttribute("order", orderList);
            return "order";
        }
    }

    @GetMapping("/check-out")
    public String checkout_page(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/custlogin";
        } else {
            CustomerDao customer = customerServiceImpl.getCustomer(principal.getName());
            if (customer.getCity() == null || customer.getAddress() == null || customer.getPhoneNumber() == null) {
                model.addAttribute("info","Please Fill Your All Details Before Checkout....");
                List<City> cities = cityServiceImpl.findAll();
                List<Country> countries = countryServiceImpl.findALl();
                model.addAttribute("cities",cities);
                model.addAttribute("countries",countries);
                model.addAttribute("customer",customer);
                return "customer-Info";
            }
            else {
                ShoppingCart shoppingCart = customerServiceImpl.findByUsername(principal.getName()).getShoppingCart();
                model.addAttribute("customer",customer);
                model.addAttribute("cart",shoppingCart);
                model.addAttribute("grandTotal",shoppingCart.getTotalItems());
                return "checkout";
            }

        }
    }
}
