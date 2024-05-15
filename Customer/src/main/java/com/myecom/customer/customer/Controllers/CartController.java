package com.myecom.customer.customer.Controllers;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Model.ShoppingCart;
import com.myecom.gallery.gallery.Services.Impl.CustomerServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.ProductServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.ShoppingCartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartServiceImpl shoppingCartService;
    private final ProductServiceImpl productService;
    private final CustomerServiceImpl customerService;
    @PostMapping("/add-to-cart")
    public String addtocart(@RequestParam("id")Long id, @RequestParam(value = "quantity",required = false,defaultValue = "1")int quantity,
                            Model model,
                            HttpServletRequest request,
                            Principal principal, HttpSession session)
    {
        ProductDao productDao = productService.getById(id);
        if(principal ==null) return "redirect:/custlogin";
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productDao,quantity,username);
        session.setAttribute("totalItems",shoppingCart.getTotalItems());
        model.addAttribute("grandTotal",shoppingCart.getTotalPrice());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:/cart";
    }
    @GetMapping("/cart")
    public String getcart(Model model,Principal principal,HttpSession session)
    {
        if(principal == null) return "redirect:/custlogin";
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getShoppingCart();
        if(cart == null) {
            model.addAttribute("check");
        }
        if(cart!=null) {
            model.addAttribute("grandTotal",cart.getTotalPrice());
        }
        model.addAttribute("shoppingCart",cart);
        session.setAttribute("totalItems",cart.getTotalItems());
        return "cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDao productDao = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.updateCart(productDao, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deetecart(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/custlogin";
        }
        ProductDao productDao = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.removeItemToCart(productDao,username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }
}
