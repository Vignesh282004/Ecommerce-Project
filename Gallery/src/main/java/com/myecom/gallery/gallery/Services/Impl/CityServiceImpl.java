package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Model.City;
import com.myecom.gallery.gallery.Repository.CityRepo;
import com.myecom.gallery.gallery.Services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;

    @Override
    public List<City> findAll() {
        return cityRepo.findAll();
    }
}
