package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<ProductDao> products();
    List<ProductDao> allProducts();

    //crud on Products
    Product save(MultipartFile multipartFile,ProductDao productDao);
    Product update(MultipartFile multipartFile,ProductDao productDao);
    void enableById(Long id);
    void deleteById(Long id);
    ProductDao getById(Long id);
    Product findById(Long id);

    // extra -Functionality
    List<ProductDao> randomProduct();
    Page<ProductDao> searchProducts(int pageNo,String keyword);
    Page<ProductDao> getAllProducts(int pageNo);
    Page<ProductDao>  getAllProductsForCustomer(int pageNo);

    List<ProductDao> findAllByCategory(String category);
    List<ProductDao> filterHighProducts();

    List<ProductDao> filterLowerProducts();

    List<ProductDao> listViewProducts();

    List<ProductDao> findByCategoryId(Long id);

    List<ProductDao> searchProducts(String keyword);



}
