package com.myecom.customer.customer.Controllers;

import com.myecom.gallery.gallery.Dao.CustomerDao;
import com.myecom.gallery.gallery.Model.City;
import com.myecom.gallery.gallery.Model.Country;
import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Model.ShoppingCart;
import com.myecom.gallery.gallery.Repository.CustomerRepo;
import com.myecom.gallery.gallery.Services.Impl.CityServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.CountryServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.CustomerServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.MailServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepo customerRepo;
    private final CustomerServiceImpl customerService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CityServiceImpl cityService;
    private final CountryServiceImpl countryService;
    private  final MailServiceImpl mailService;

    @GetMapping("/custregister")
    public String register_get(Model model) {
        model.addAttribute("title", "Register Customer PAge");
        model.addAttribute("customerDao", new CustomerDao());
        return "custregister";
    }

    @PostMapping("/do-register")
    public String register(@Valid @ModelAttribute("customerDao") CustomerDao customerDao, BindingResult result, Model model, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDao", customerDao);
                return "custregister";
            }
            String username = customerDao.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDao", customerDao);
                attributes.addFlashAttribute("error", "Customer with this Username is alderady Registered.... ");
                return "custregister";
            }
            if (customerDao.getPassword().equals(customerDao.getConfirmPassword())) {
                customerDao.setPassword(passwordEncoder.encode(customerDao.getPassword()));
                customerService.save(customerDao);
                attributes.addFlashAttribute("success", "REgisted Customer Success.");
            } else {
                attributes.addFlashAttribute("error", "Passwords Dont Match");
                model.addAttribute("customerDao", customerDao);
                return "custregister";
            }
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Server Issue....");
        }
        return "custregister";
    }

    @RequestMapping(value = "/custlogin", method = RequestMethod.GET)
    public String login(Model model,RedirectAttributes attributes) {
        model.addAttribute("title", "Login Page");
        attributes.addFlashAttribute("error","Invalid pAssword or Username ");
        return "custlogin";
    }

    @GetMapping("/contact")
    public String cantact(Model model) {
        model.addAttribute("title", "contact page");
        model.addAttribute("page", "contact");
        return "contact";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home(Model model, HttpSession session, Principal principal) {
        if (principal == null) {
            return "redirect:/custlogin";
        }
        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            ShoppingCart cart = customer.getShoppingCart();
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            }
        }
        return "home";
    }
@GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/profile")
    public String customer_profile(Model model, Principal principal) {
        if (principal == null) return "redirect:/custlogin";
        String username = principal.getName();
        CustomerDao customer = customerService.getCustomer(username);
        List<City> cities = cityService.findAll();
        List<Country> countryList = countryService.findALl();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countryList);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-info";
    }

    @PostMapping("/update-profile")
    public String update_profile(@Valid @ModelAttribute("customer") CustomerDao customerDao, BindingResult result,
                                 Model model, Principal principal, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/custlogin";
        }
        String username = principal.getName();
        CustomerDao customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findALl();
        List<City> cities = cityService.findAll();
        model.addAttribute("customer",customer);
        model.addAttribute("countries", countryList);
        model.addAttribute("cities", cities);
        if (result.hasErrors()) {
            return "customer-info";
        }
        customerService.update(customerDao);
        CustomerDao customerUpdate = customerService.getCustomer(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/custlogin";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/custlogin";
        } else {
            CustomerDao customer = customerService.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePassword(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "change-password";
            }
        }
    }

    // forsgor -password
    @GetMapping("/forgot-password")
    public String forgot_password() {
        return "forgot-pass";
    }
    @GetMapping("/blob")
    public String blob() {
        return "blog";
    }


    @PostMapping("/send-otp")
    public String sendotp(@RequestParam String email,RedirectAttributes attributes,HttpSession session,Model model)
    {
        System.out.println(email);
        Random random = new Random();
        int otp = random.nextInt(999999);
        System.out.println(otp);
        String ex_mail = email;
        String subject = "This is a Mial from Sprigboot Application";
        String msg = "Your Otp for Re-setting Password : "+otp+"";
        boolean flag = this.mailService.sendmail(ex_mail,subject,msg);
        if(flag)
        {
            session.setAttribute("oldotp", otp);
            session.setAttribute("email", email);
            model.addAttribute("success","Otp Was Sent Suucessfully ! Check your Mail");
            return "otp-check";
        }
        else
        {
            attributes.addFlashAttribute("failed","Otp Was Not Sent ___ Server Down <===========>");
            session.setAttribute("message", "OOPS ?* Server Runtime Error ------- Otp Not Sent");
            return "change_Mpassword";
        }
    }

    @PostMapping("/otp-check")
    public String verify_otp(@RequestParam int otp,HttpSession session,Model model)
    {
        int my_otp = (int)session.getAttribute("oldotp");
        String email = (String)session.getAttribute("email");
        if(otp == my_otp)
        {
            Customer customer = customerService.findByUsername(email);
            if(customer == null)
            {
                model.addAttribute("failed","No User Existed..Please Go back and Register First *");
                return "otp-check";
            }
            else {
                  return "change_Mpassword";
            }
        }
        else
        {
            model.addAttribute("failed","You Have Entered Wrong Otp !! Please Enter Latest Otp");
            return "otp-check";
        }
    }
        @PostMapping("/change_Mpass")
        public String change_Mpassword(@RequestParam String password,Model model,HttpSession session)
        {
            try {
                String email = (String) session.getAttribute("email");
                Customer customer = customerRepo.findByUsername(email);
                customer.setPassword(this.passwordEncoder.encode(password));
                this.customerRepo.save(customer);
                model.addAttribute("success","Password Changed Successfully.");
                return "change_Mpassword";
            }
            catch (Exception e)
            {
                model.addAttribute("failed","Password Didn't Change");
                System.out.println(e);
            }
            return "change_Mpassword";
        }
}
