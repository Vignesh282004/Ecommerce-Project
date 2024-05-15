package com.myecom.admin.admin;

import jakarta.servlet.ServletOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(scanBasePackages = {"com.myecom.gallery.gallery.*","com.myecom.admin.admin.*"})
@EnableJpaRepositories(value = "com.myecom.gallery.gallery.Repository")
@EntityScan(value = "com.myecom.gallery.gallery.Model")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("Application Started .........."+args);
    }

}
