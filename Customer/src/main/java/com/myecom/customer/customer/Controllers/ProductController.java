package com.myecom.customer.customer.Controllers;

import com.myecom.gallery.gallery.Dao.CategoryDao;
import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.Category;
import com.myecom.gallery.gallery.Services.Impl.CategoryServiceImpl;
import com.myecom.gallery.gallery.Services.Impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final CategoryServiceImpl categoryService;
    @GetMapping("/menu")
    public String menu(Model model)
    {
        model.addAttribute("title","Products");
        List<Category> categoryList = categoryService.findAllByActivatedTrue();
        List<ProductDao> productDaos = productService.products();
        model.addAttribute("categories",categoryList);
        model.addAttribute("products",productDaos);
        return "index";
    }

    @GetMapping("/product-detail/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        ProductDao product = productService.getById(id);
        List<ProductDao> productDtoList = productService.findAllByCategory(product.getCategory().getName());
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Product Detail");
        model.addAttribute("page", "Product Detail");
        model.addAttribute("productDetail", product);
        return "product-detail";
    }
@GetMapping("/shop")
    public String shop(Model model)
{
    List<CategoryDao> categories = categoryService.getCategoriesAndSize();
    model.addAttribute("categories",categories);
    List<ProductDao> products = productService.randomProduct();
    model.addAttribute("products",products);
    List<ProductDao> listView = productService.listViewProducts();
    model.addAttribute("listView",listView);
    return "shop";
}
@GetMapping("/high-price")
public String filerHigh(Model model)
{
    List<CategoryDao> categoryDaos = categoryService.getCategoriesAndSize();
    model.addAttribute("categories",categoryDaos);
    List<ProductDao> productDaos = productService.filterHighProducts();
    model.addAttribute("products",productDaos);
    List<ProductDao> productDaoList = productService.listViewProducts();
    model.addAttribute("productDaoList",productDaoList);
    return "shop";
}

    @GetMapping("/low-price")
    public String filterLow(Model model)
    {
        List<CategoryDao> categoryDaos = categoryService.getCategoriesAndSize();
        model.addAttribute("categories",categoryDaos);
        List<ProductDao> productDaos = productService.filterLowerProducts();
        model.addAttribute("products",productDaos);
        List<ProductDao> productDaoList = productService.listViewProducts();
        model.addAttribute("productDaoList",productDaoList);
        return "shop";
    }
@GetMapping("/search-product")
    public String search_prods(Model model, @RequestParam("keyword")String keyword)
{
    List<CategoryDao> categories = categoryService.getCategoriesAndSize();
    model.addAttribute("categories",categories);
    List<ProductDao> productDaos = productService.searchProducts(keyword);
    model.addAttribute("products",productDaos);
    List<ProductDao> productDaoList = productService.listViewProducts();
    model.addAttribute("productViews",productDaoList);
    return "products";
}
@GetMapping("/find-products/{id}")
public String find_prods_by_cat(@PathVariable("id")Long id,Model model)
{
    List<CategoryDao> categoryDaos  = categoryService.getCategoriesAndSize();
    model.addAttribute("categories",categoryDaos);
    List<ProductDao> products = productService.findByCategoryId(id);
    model.addAttribute("products",products);
    List<ProductDao> productDaoList = productService.listViewProducts();
    model.addAttribute("productViews",productDaoList);
    return "products";
}
}
