package com.myecom.gallery.gallery.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDao {

    private Long id;
    private String name;
    private Long productSize;
}
