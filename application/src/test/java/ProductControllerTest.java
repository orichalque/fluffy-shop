import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.ProductNotFoundException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductsRepository productsRepository;

    private MockMvc mockMvc;

    private ArrayList<Product> products;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        products = new ArrayList<>();
        Product product = new Product();

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

        Mockito.reset(productsRepository);
    }

    // cas passants

    @Test
    public void testGetAllProducts() throws Exception {

        Mockito.when(productsRepository.findAll()).then(invocationOnMock -> products);
        mockMvc.perform(get("/products").accept(MediaType.ALL))
                                            .andExpect(status().isOk())
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


    @Test
    public void testGetAllProductsPaginated() throws Exception {

        Mockito.when(productsRepository.findPage(1, 1)).then(invocationOnMock -> products.subList(0, 1));
        mockMvc.perform(get("/products?page=1&size=1").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testFindById() throws Exception {
        Mockito.when(productsRepository.find(Mockito.anyString())).then(invocationOnMock -> OBJECT_MAPPER.writeValueAsString(products.get(0)));
        mockMvc.perform(get("/product/8f987fc2-e11f-474c-91c5-5f49104e471b").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.price", is(50.0)))
                .andExpect(jsonPath("$.quantity", is(5)))
                .andExpect(jsonPath("$.id", is("8f987fc2-e11f-474c-91c5-5f49104e471b")));
    }

    @Test
    public void testOrderProduct() throws Exception {
        Mockito.when(productsRepository.find(Mockito.anyString())).then(invocationOnMock -> OBJECT_MAPPER.writeValueAsString(products.get(0)));
        mockMvc.perform(post("/product/8f987fc2-e11f-474c-91c5-5f49104e471b/order/2").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.price", is(50.0)))
                .andExpect(jsonPath("$.quantity", is(3))) //Assert that the product quantity has been reduced
                .andExpect(jsonPath("$.id", is("8f987fc2-e11f-474c-91c5-5f49104e471b")));
    }

    //cas non passants
    @Test
    public void testNotFound() throws Exception {
        Mockito.when(productsRepository.find(Mockito.anyString())).thenThrow(new ProductNotFoundException("Cannot find the product"));
        mockMvc.perform(get("/product/id-not-in-the-database").accept(MediaType.ALL))
                .andExpect(status().is(404));
    }

    @Test
    public void testNotEnoughProducts() throws Exception {
        Product product = new Product();
        product.setQuantity(2);
        String productAsString = OBJECT_MAPPER.writeValueAsString(product);

        Mockito.when(productsRepository.find(Mockito.anyString())).thenReturn(productAsString);
        mockMvc.perform(post("/product/id/order/5").accept(MediaType.ALL))
                .andExpect(status().is(404));
    }
}
