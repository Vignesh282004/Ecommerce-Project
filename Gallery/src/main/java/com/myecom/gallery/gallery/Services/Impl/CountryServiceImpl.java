package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Model.Country;
import com.myecom.gallery.gallery.Model.Customer;
import com.myecom.gallery.gallery.Repository.CountryRepo;
import com.myecom.gallery.gallery.Services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;


    @Override
    public List<Country> findALl() {
            return countryRepo.findAll();
    }


}
