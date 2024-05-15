package com.myecom.gallery.gallery.Services;

import com.myecom.gallery.gallery.Dao.CategoryDao;
import com.myecom.gallery.gallery.Model.Category;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    Category update(Category category);
    List<Category> findAll();
    List<Category> findAllByActivatedTrue();
    void deleteById(Long id);
    void enableById(Long id);
    Optional<Category> findById(Long id);
    List<CategoryDao> getCategoriesAndSize();

}
