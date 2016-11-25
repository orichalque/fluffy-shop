import com.alma.group8.application.controller.ProductController;
import com.alma.group8.infrastructure.factories.ProductFactoryImpl;
import com.alma.group8.application.handlers.ExceptionHandling;
import com.alma.group8.api.interfaces.ProductFactory;
import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.domain.interfaces.ProductsRepository;
import com.alma.group8.domain.service.ProductServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Thibault on 21/11/2016.
 * Configuration class
 */
@Configuration
@EnableWebMvc
public class TestConfiguration {

    @Bean
    public ProductsRepository productsRepository() {
        return Mockito.mock(ProductsRepository.class);
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }

    @Bean
    public ProductController productController() {
        return new ProductController();
    }

    @Bean
    public ExceptionHandling exceptionHandling() { return new ExceptionHandling(); }

    @Bean
    public ProductFactory productFactory() {
        return new ProductFactoryImpl();
    }
}
