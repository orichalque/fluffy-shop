package com.alma.group8.configuration;

import com.alma.group8.handlers.ExceptionHandling;
import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.alma.group8.repository.MongoRepository;
import com.alma.group8.model.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thibault on 10/11/2016.
 * Configure the Spring Context
 */
@Configuration
@ComponentScan({"com.alma.group8.controller"})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{

    /**
     * Create the {@link ProductsRepository} and add it in the SpringContext
     * @return the {@link org.springframework.beans.factory.annotation.Autowired} {@link MongoRepository}
     */
    @Bean
    public ProductsRepository productsRepository() {
        return new MongoRepository();
    }

    /**
     * Create the {@link ProductService} and add it in the SpringContext
     * @return the {@link org.springframework.beans.factory.annotation.Autowired} {@link ProductService}
     */
    @Bean
    public ProductService<FunctionalException> productService() {
        return new ProductServiceImpl();
    }

    /**
     * Create the ExceptionHandling and add it in the spring context
     * He'll catch the exceptions throwed by the controller and build a custom http servlet answer
     * @return the newly created ExceptionHandling
     */
    @Bean
    public ExceptionHandling exceptionHandling() {
        return new ExceptionHandling();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("*");
        registry.addMapping("/admin/**").allowedOrigins("fluffy-stock-presentation.herokuapp.com");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(stringConverter);

    }
}
