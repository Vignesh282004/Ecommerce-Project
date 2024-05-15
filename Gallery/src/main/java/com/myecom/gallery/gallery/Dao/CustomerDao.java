package com.myecom.gallery.gallery.Dao;

import com.myecom.gallery.gallery.Model.City;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDao {
    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
     private String firstName;
    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
     private  String lastName;
    @Size(min = 3, max = 100, message = "Username name contains 3-10 characters")
     private String username;
    @Size(min = 6, max = 10, message = "Password  contains 6-10 characters")
     private String password;
    @Size(min = 7, max = 10, message = "Phone number  contains 7-10 characters")
     private String PhoneNumber;

    private String address;
    private String confirmPassword;
    private City city;
    private String country;
    private String image;

}
