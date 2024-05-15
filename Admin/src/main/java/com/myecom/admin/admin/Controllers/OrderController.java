package com.myecom.admin.admin.Controllers;

import com.myecom.gallery.gallery.Model.Order;
import com.myecom.gallery.gallery.Services.Impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    @GetMapping("/orders")
    public String getOrder(Model model, Principal principal)
    {
        if(principal == null) {
            return "redirect:/login";
        }
        else
        {
            List<Order> orderList = orderService.findALlOrders();
            model.addAttribute("orders",orderList);
            return "orders";
        }
    }

    @RequestMapping(value = "/accept-order",method = {RequestMethod.PUT,RequestMethod.GET})
    public String acceptOrder(Long id, RedirectAttributes attributes, Principal principal)
    {
        if(principal == null) {
            return "redirect:/login";
        }
        else
        {
            orderService.acceptOrder(id);
            attributes.addFlashAttribute("success","ORder accpeted by admin");
            return "redirect:/orders";
        }
    }

    @RequestMapping(value = "/delete-order",method = {RequestMethod.PUT,RequestMethod.GET})
    public String delOrder(Long id,Principal principal)
    {
        if(principal == null) {
            return "redirect:/login";
        }
        else
        {
            orderService.cancaelOrder(id);
            return "redirect:/orders";
        }
    }
}
