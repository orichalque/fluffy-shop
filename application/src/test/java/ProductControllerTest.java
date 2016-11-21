import com.alma.group8.model.Product;
import com.alma.group8.model.interfaces.ProductsRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Thibault on 21/11/2016.
 * Test the {@link com.alma.group8.controller.ProductController}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductsRepository productsRepository;

    private MockMvc mockMvc;

    private Product product;

    private ArrayList<Product> products;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        products = new ArrayList<>();
        product = new Product();

        product.setQuantity(5);
        product.setDescription("description");
        product.setName("name");
        UUID uuid = UUID.fromString("8f987fc2-e11f-474c-91c5-5f49104e471b");
        product.setId(uuid);
        product.setPrice(50.);
        products.add(product);

        Product product2 = new Product();
        product2.setQuantity(3);
        product2.setDescription("description2");
        product2.setName("name2");
        uuid = UUID.fromString("e245e8cb-d616-4631-beb1-9cf8b4894d91");
        product2.setId(uuid);
        product2.setPrice(20.);
        products.add(product2);


    }

    @Test
    public void testGetAllProducts() throws Exception {

        Mockito.when(productsRepository.findAll()).then(invocationOnMock -> products);
        mockMvc.perform(get("/products").accept(MediaType.ALL))
                                            .andExpect(status().isOk())
                                            .andDo(print())
                                            .andExpect(jsonPath("$[0].name", is("name")))
                                            .andExpect(jsonPath("$[0].description", is("description")))
                                            .andExpect(jsonPath("$[0].price", is(50.0)))
                                            .andExpect(jsonPath("$[0].quantity", is(5)))
                                            .andExpect(jsonPath("$[0].id", is("8f987fc2-e11f-474c-91c5-5f49104e471b")))
                                            .andExpect(jsonPath("$[1].name", is("name2")))
                                            .andExpect(jsonPath("$[1].description", is("description2")))
                                            .andExpect(jsonPath("$[1].price", is(20.0)))
                                            .andExpect(jsonPath("$[1].quantity", is(3)))
                                            .andExpect(jsonPath("$[1].id", is("e245e8cb-d616-4631-beb1-9cf8b4894d91")));
    }

    @Ignore
    @Test
    public void testGetAllProductsPaginated() throws Exception {

        Mockito.when(productsRepository.findAll()).then(invocationOnMock -> products);
        mockMvc.perform(get("/products?page=1&size=1").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
