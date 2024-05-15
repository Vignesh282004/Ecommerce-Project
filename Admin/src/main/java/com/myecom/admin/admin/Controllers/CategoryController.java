package com.myecom.admin.admin.Controllers;

import com.myecom.gallery.gallery.Model.Category;
import com.myecom.gallery.gallery.Services.Impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {


    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/categories")
    public String categories(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("Title","Categories");
        List<Category> categories = categoryServiceImpl.findAll();
        model.addAttribute("title","Categories");
        model.addAttribute("size",categories.size());
        model.addAttribute("categories",categories);
        model.addAttribute("categoryNew",new Category());
        return "categories";
    }
    @PostMapping("/save-cat")
    public String save(@ModelAttribute("categoryNew")Category category, Model model, RedirectAttributes attributes)
    {
        try {
            categoryServiceImpl.save(category);
            model.addAttribute("categoryNew",category);
            attributes.addFlashAttribute("success","Category Added....");
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error","Duplicate name of category, please check again!");
        }
        catch (Exception e1) {
            e1.printStackTrace();
            model.addAttribute("categoryNew",category);
            attributes.addFlashAttribute("error","Server Issue");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById(Long id) {
        return categoryServiceImpl.findById(id);
    }
    @GetMapping("/update-cat")
    public String update(Category category,RedirectAttributes attributes,Model model)
    {
        try {
            model.addAttribute("catid",category.getId());
            model.addAttribute("catname",category.getName());
        categoryServiceImpl.update(category);
        attributes.addFlashAttribute("success","category Updated.......");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value = "/delete-cat",method = {RequestMethod.GET,RequestMethod.PUT})
    public String delete(Long id,RedirectAttributes attributes){
        try {
            categoryServiceImpl.deleteById(id);
            attributes.addFlashAttribute("success","Category Deleted");
    } catch (DataIntegrityViolationException e1) {
        e1.printStackTrace();
        attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
    } catch (Exception e2) {
        e2.printStackTrace();
        attributes.addFlashAttribute("error", "Server Issue !!  please check again!");
    }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-cat",method = {RequestMethod.PUT,RequestMethod.GET})
    public String enable(Long id,RedirectAttributes attributes){
        try {
            categoryServiceImpl.enableById(id);
            attributes.addFlashAttribute("success","Category Enabled");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Server Issue");
        }
        return "redirect:/categories";
    }
}


































