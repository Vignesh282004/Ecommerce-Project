package com.myecom.gallery.gallery.Dao;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDao {

    @Size(min = 3,message = "firstname should be atleast  3 Characters Long")
    private String firstname;
    @Size(min = 3,message = "lastname should be atleast   3 Characters Long")
    private String lastname;

    private String username;
    @Size(min = 6,message = "Password should be atleast  6 Characters Long")
    private String password;
    private String repeat_password;

}
