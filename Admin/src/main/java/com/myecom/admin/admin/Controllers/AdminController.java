package com.myecom.admin.admin.Controllers;

import com.myecom.gallery.gallery.Dao.AdminDao;
import com.myecom.gallery.gallery.Model.Admin;
import com.myecom.gallery.gallery.Repository.AdminRepo;
import com.myecom.gallery.gallery.Services.Impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title","Login Page");
        return "login";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title","Register Page");
        model.addAttribute("adminDao",new AdminDao());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

@PostMapping("/register-new")
    public String registerNew(@ModelAttribute("adminDao")AdminDao adminDao, BindingResult result,Model model) {
        try {

            if(result.hasErrors()) {
                model.addAttribute("adminDao",adminDao);
                return "register";
            }
            String username = adminDao.getUsername();
            Admin admin = adminRepo.findByUsername(username);
            if(admin!=null) {
                model.addAttribute("adminDao", adminDao);
                model.addAttribute("errors", "An Admin With this Email Aldready Registered...");
                System.out.println("Admin not null");
                return "register";
            }
            if(adminDao.getPassword().equals(adminDao.getRepeat_password())) {
                adminDao.setPassword(passwordEncoder.encode(adminDao.
                        getPassword()));
                adminServiceImpl.save(adminDao);
                System.out.println("Adimn Registered");
                model.addAttribute("success","Registered Successfully");
                model.addAttribute("adminDao",adminDao);
            }else  {
                model.addAttribute("adminDao",adminDao);
                model.addAttribute("passwordError","PassWords Dont Match ....");
                System.out.println("Passwords dont Match....");
            }
        }catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("errors","Server Logic Issue....");
        }
        return "register";
    }
    @GetMapping("/shop")
    public String shop() {
        return "shop";
    }

}
