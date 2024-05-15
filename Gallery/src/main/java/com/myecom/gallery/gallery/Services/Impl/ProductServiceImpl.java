package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Dao.ProductDao;
import com.myecom.gallery.gallery.Model.Product;
import com.myecom.gallery.gallery.Repository.ProductRepo;
import com.myecom.gallery.gallery.Services.ProductService;
import com.myecom.gallery.gallery.UpImage.ImageUp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ImageUp upload_image;

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<ProductDao> products() {
       return transferData(productRepo.getALlProduct());
    }

    @Override
    public List<ProductDao> allProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDao> productDaos = transferData(products);
        return productDaos;
    }

    @Override
    public Product save(MultipartFile prod_image, ProductDao productDao) {
        Product product = new Product();
        try {
                if(prod_image == null) {
                    product.setImage(null);
                }else {
                    upload_image.uploadNew(prod_image);
                    product.setImage(Base64.getEncoder().encodeToString(prod_image.getBytes()));
                }
                product.setName(productDao.getName());
                product.setDescription(productDao.getDescription());
                product.setCostPrice(productDao.getCostPrice());
                product.setCurrentQuantity(productDao.getCurrentQuantity());
                product.setCategory(productDao.getCategory());
                product.set_deleted(false);
                product.set_activated(true);
             return  productRepo.save(product);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile update_image, ProductDao productDao) {
        try {
            Product update_product = productRepo.getReferenceById(productDao.getId());
            if(update_image.getBytes().length > 0) {
                if(upload_image.if_image_exists(update_image)) {
                    update_product.setImage(update_product.getImage());
                }
                else {
                    upload_image.uploadNew(update_image);
                    update_product.setImage(Base64.getEncoder().encodeToString(update_image.getBytes()));
                }
            }
            update_product.setId(productDao.getId());
            update_product.setName(productDao.getName());
            update_product.setDescription(productDao.getDescription());
            update_product.setCategory(productDao.getCategory());
            update_product.setCostPrice(productDao.getCostPrice());
            update_product.setSalePrice(productDao.getSalePrice());
            update_product.setCurrentQuantity(productDao.getCurrentQuantity());
            return productRepo.save(update_product);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepo.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepo.getById(id);
        product.set_activated(false);
        product.set_deleted(true);
        productRepo.save(product);
    }

    @Override
    public ProductDao getById(Long id) {
        ProductDao productDao = new ProductDao();
        Product product = productRepo.getById(id);
        productDao.setId(product.getId());
        productDao.setName(product.getName());
        productDao.setDescription(product.getDescription());
        productDao.setCategory(product.getCategory());
        productDao.setCostPrice(product.getCostPrice());
        productDao.setCurrentQuantity(product.getCurrentQuantity());
        productDao.setSalePrice((product.getSalePrice()));
        productDao.setImage(product.getImage());
        return productDao;
    }

    @Override
    public Product findById(Long id) {
        return productRepo.findById(id).get();
    }

    @Override
    public List<ProductDao> randomProduct() {
        return transferData(productRepo.randomProduct());
    }

    @Override
    public Page<ProductDao> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepo.findAllByNameOrDescription(keyword);
        List<ProductDao> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductDao> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    @Override
    public Page<ProductDao> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDao> productDtoLists = this.allProducts();
        Page<ProductDao> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }

    @Override
    public Page<ProductDao> getAllProductsForCustomer(int pageNo) {
        return null;
    }

    @Override
    public List<ProductDao> findAllByCategory(String category) {
       return transferData(productRepo.findAllByCategory(category));
    }

    @Override
    public List<ProductDao> filterHighProducts() {
        return transferData(productRepo.filterHighProducts());
    }

    @Override
    public List<ProductDao> filterLowerProducts() {
        return transferData(productRepo.filterLowerProducts());
    }

    @Override
    public List<ProductDao> listViewProducts() {
        return transferData(productRepo.listViewProduct());
    }

    @Override
    public List<ProductDao> findByCategoryId(Long id) {
        return transferData(productRepo.getProductsByCategoryId(id));
    }

    @Override
    public List<ProductDao> searchProducts(String keyword) {
        return transferData(productRepo.searchProducts(keyword));
  }

  public List<ProductDao> transferData(List<Product>products)
  {
     List<ProductDao> productDaos = new ArrayList<>();
     for(Product product : products)
     {
         ProductDao productDao = new ProductDao();
         productDao.setId(product.getId());
         productDao.setName(product.getName());
         productDao.setDescription(product.getDescription());
         productDao.setCategory(product.getCategory());
         productDao.setCostPrice(product.getCostPrice());
         productDao.setCurrentQuantity(product.getCurrentQuantity());
         productDao.setSalePrice((product.getSalePrice()));
         productDao.setImage(product.getImage());
         productDao.setActivated(product.is_activated());
         productDao.setDeleted(product.is_deleted());
         productDaos.add(productDao);
     }
        return productDaos;
  }
    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }
}























