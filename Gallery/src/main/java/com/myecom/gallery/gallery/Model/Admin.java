package com.myecom.gallery.gallery.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "admins",uniqueConstraints = @UniqueConstraint(columnNames = {"username","image"}))
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id")
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String image;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "admins_roles",joinColumns = @JoinColumn(name = "admin_id",referencedColumnName = "admin_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private List<Role> roles;


}
