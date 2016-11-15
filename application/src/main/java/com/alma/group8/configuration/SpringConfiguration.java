package com.alma.group8.configuration;

import model.interfaces.ProductsRepository;
import com.alma.groupe8.repository.MongoRepository;
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
public class SpringConfiguration extends WebMvcConfigurerAdapter{

    @Bean
    public ProductsRepository productsRepository() {
        return new MongoRepository();
    }

}
