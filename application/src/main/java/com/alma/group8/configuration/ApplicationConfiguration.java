package com.alma.group8.configuration;

import com.alma.group8.interfaces.ProductService;
import model.Product;
import model.interfaces.ProductsRepository;
import com.alma.groupe8.repository.MongoRepository;
import model.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Thibault on 10/11/2016.
 * Configure the Spring Context
 */
@Configuration
@ComponentScan({"com.alma.group8.controller"})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{

    @Bean
    public ProductsRepository productsRepository() {
        return new MongoRepository();
    }

    @Bean
    ProductService<Product> productService() {
        return new ProductServiceImpl();
    }

}
