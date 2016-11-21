import com.alma.group8.controller.ProductController;
import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.alma.group8.model.service.ProductServiceImpl;
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
    public ProductService<FunctionalException> productService() {
        return new ProductServiceImpl();
    }

    @Bean
    public ProductController productController() {
        return new ProductController();
    }
}
