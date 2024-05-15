package com.myecom.gallery.gallery.Repository;

import com.myecom.gallery.gallery.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepo extends JpaRepository<Country,Long> {

}
