import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.api.interfaces.UserRepository;
import com.alma.group8.application.controller.ProductController;
import com.alma.group8.application.controller.UserController;
import com.alma.group8.application.handlers.ExceptionHandling;
import com.alma.group8.application.util.SoapMailVerifier;
import com.alma.group8.domain.model.Product;
import com.alma.group8.domain.service.ProductServiceImpl;
import com.alma.group8.infrastructure.factories.GenericFactory;
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
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public SoapMailVerifier soapMailVerifier() { return Mockito.mock(SoapMailVerifier.class); }
    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }

    @Bean
    public ProductController productController() {
        return new ProductController();
    }

    @Bean
    public UserController userController() { return new UserController(); }

    @Bean
    public ExceptionHandling exceptionHandling() { return new ExceptionHandling(); }

    @Bean
    public FunctionalFactory productFactory() {
        return new GenericFactory(Product.class);
    }


}
