package com.myecom.gallery.gallery.Dao;

import com.myecom.gallery.gallery.Model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDao {
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private  double costPrice;
    private double salePrice;
    private String image;
    private Category category;
    private boolean activated;
    private boolean deleted;
    private String currentPage;
}
