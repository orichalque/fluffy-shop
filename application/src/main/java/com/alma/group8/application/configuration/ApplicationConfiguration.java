package com.alma.group8.application.configuration;

import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.api.interfaces.UserRepository;
import com.alma.group8.application.handlers.ExceptionHandling;
import com.alma.group8.domain.factories.ProductFactoryImpl;
import com.alma.group8.domain.factories.UserFactoryImpl;
import com.alma.group8.domain.model.Product;
import com.alma.group8.domain.model.User;
import com.alma.group8.domain.service.ProductServiceImpl;
import com.alma.group8.infrastructure.factories.GenericFactory;
import com.alma.group8.infrastructure.repository.MongoProductRepository;
import com.alma.group8.infrastructure.repository.MongoUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thibault on 10/11/2016.
 * Configure the Spring Context
 */
@Configuration
@ComponentScan({"com.alma.group8.application.controller"})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{

    /**
     * Create the {@link ProductsRepository} and add it in the SpringContext
     * @return the {@link org.springframework.beans.factory.annotation.Autowired} {@link MongoProductRepository}
     */
    @Bean
    public ProductsRepository productsRepository() {
        return new MongoProductRepository();
    }

    /**
     * Create the {@link UserRepository} and add it in the Sprring Context
     * @return the {@link org.springframework.beans.factory.annotation.Autowired} {@link MongoUserRepository}
     */
    @Bean
    public UserRepository userRepository() {
        return new MongoUserRepository();
    }

    /**
     * Create the {@link ProductService} and add it in the SpringContext
     * @return the {@link org.springframework.beans.factory.annotation.Autowired} {@link ProductService}
     */
    @Bean
    public ProductService productService() {
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

    /**
     * Create a FunctionalFactory and add it in the spring context
     * @return the newly created {@link FunctionalFactory}
     */
    @Bean(name = "productFactory")
    public FunctionalFactory productFactory() {
        return new GenericFactory(Product.class);
    }


    /**
     * Create a {@link UserFactoryImpl}
     * @return the {@link UserFactoryImpl}
     */
    @Bean(name = "userFactory")
    public FunctionalFactory userFactory() {
        return new GenericFactory(User.class);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("*");
        registry.addMapping("/admin/**").allowedOrigins("*").allowedMethods("POST", "GET", "DELETE");//FIXME set * for tests in localhost("https://fluffy-stock-presentation.herokuapp.com");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(stringConverter);
    }
}
