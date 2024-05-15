package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Dao.CategoryDao;
import com.myecom.gallery.gallery.Model.Category;
import com.myecom.gallery.gallery.Repository.CategoryRepo;
import com.myecom.gallery.gallery.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Category save(Category category) {
        Category category1 = new Category();
        category1.setName(category.getName());
        category1.setActivated(true);
        category1.setDeleted(false);
        return categoryRepo.save(category1);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepo.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepo.save(categoryUpdate);
    }

    @Override
    public List<Category> findAll() {
      return categoryRepo.findAll();
    }

    @Override
    public List<Category> findAllByActivatedTrue() {
        return categoryRepo.findAllByActivatedTrue();
    }

    @Override
    public void deleteById(Long id) {
    Category category_delete = categoryRepo.getById(id);
    category_delete.setDeleted(true);
    category_delete.setActivated(false);
    categoryRepo.save(category_delete);
    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepo.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        categoryRepo.save(category);

    }

    @Override
    public Optional<Category> findById(Long id) {
       return categoryRepo.findById(id);
    }

    @Override
    public List<CategoryDao> getCategoriesAndSize() {
        List<CategoryDao> categories = categoryRepo.getCategoriesAndSize();
        return categories;
    }
}
