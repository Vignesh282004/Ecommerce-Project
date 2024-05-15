package com.myecom.customer.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.myecom.gallery.gallery.*","com.myecom.customer.customer.*"})
@EnableJpaRepositories(value = "com.myecom.gallery.gallery.Repository")
@EntityScan(value = "com.myecom.gallery.gallery.Model")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
