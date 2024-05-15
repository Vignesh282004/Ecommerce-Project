package com.myecom.admin.admin.Controllers;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.Category;
import com.myecom.gallery.gallery.Services.Impl.CategoryServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/products")
    public String getallproducts(Model model, Principal principal) {
        if(principal == null) return "redirect:/login";
        List<ProductDao> products = productService.allProducts();
        model.addAttribute("products",products);
        model.addAttribute("size",products.size());
        return "products";
    }
    @GetMapping("/products/{pageNo}")
    public String allproducts(@PathVariable("pageNo")int pageNo, Principal principal , Model model)
    {
        if(principal == null) return "redirect:/login";
        Page<ProductDao> productDaos = productService.getAllProducts(pageNo);
        model.addAttribute("products",productDaos);
        model.addAttribute("pageNo",productDaos.getSize());
        model.addAttribute("currentpage",pageNo);
        model.addAttribute("totalpages",productDaos.getTotalPages());
        return "products";
    }
    @GetMapping("/search-products/{keyword}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDao> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-result";
    }
    @GetMapping("/add-prod")
    public String save(Model model ,Principal principal)
    {
        if(principal == null) return "redirect:/login";
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("title","Add Produt");
        model.addAttribute("categories",categories);
        model.addAttribute("productDao",new ProductDao());
        return "add-product";
    }
    @PostMapping("/save-prod")
    public String saveProduct(@ModelAttribute("productDao") ProductDao product,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.save(imageProduct, product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products";
    }
    @GetMapping("/enable-prod/{id}")
    public String enableprod(@PathVariable("id")Long id, RedirectAttributes attributes, Principal principal)
    {
        try
        {
            if(principal == null) return "redirect:/login";
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Product enabled successfully!");
        }catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to enable product!");
        }
        return "redirect:/products";
    }
    @GetMapping("/delete-prod/{id}")
    public String deleteprod(@PathVariable("id")Long id,RedirectAttributes attributes,Principal principal)
    {
        try
        {
            if(principal == null) return "redirect:/login";
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Product deleted successfully!");
        }catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to delete  product!");
        }
        return "redirect:/products";
    }
    @GetMapping("/update-prod/{id}")
    public String update_prod(@PathVariable("id")Long id,Model model,Principal principal)
    {
        if(principal==null) return "redirect:/login";
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDao productDao = productService.getById(id);
        model.addAttribute("title","Updateing Product");
        model.addAttribute("categories",categories);
        model.addAttribute("productDao",productDao);
        return "update-product";
    }

    @PostMapping("/update-prod/{id}")
    public String update_prods(@ModelAttribute("productDao")ProductDao productDao,RedirectAttributes attributes,@RequestParam("imageProduct")MultipartFile multipartFile,Principal principal)
    {
        try
        {
            if(principal == null) return "redirect:/login";
            productService.update(multipartFile,productDao);
            attributes.addFlashAttribute("success","Product Updated .....");
        }catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error","Cannot Update the Product");
        }
        return "redirect:/products";
    }










}
