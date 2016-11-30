import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.domain.model.Product;
import com.alma.group8.domain.model.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Thibault on 30/11/16.
 * Test on the admin controller class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private FunctionalFactory<Product> functionalFactory;

    private boolean viewed;

    @Before
    public void setUp() {
        viewed = false;
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddProduct() throws Exception {

        Mockito.doAnswer(invocationOnMock -> {
            String productAsString = (String) invocationOnMock.getArguments()[0];
            Product product = functionalFactory.deserialize(productAsString);
            Assert.assertEquals("description", product.getDescription());
            Assert.assertEquals("name", product.getName());
            Assert.assertNotNull(product.getId());
            Assert.assertEquals(ProductType.BAZAR, product.getProductType());
            Assert.assertEquals(40., product.getPrice(), 0.1);
            Assert.assertEquals(1, product.getQuantity());
            viewed = true;
            return null;
        }).when(productsRepository).store(Mockito.anyString());

        Product product = new Product();

        product.setName("name");
        product.setDescription("description");
        product.setPrice(40.);
        product.setQuantity(1);
        product.setProductType(ProductType.BAZAR);

        String productAsString = functionalFactory.serialize(product);

        Assert.assertNull(product.getId());

        mockMvc.perform(post("/admin/product").contentType(MediaType.APPLICATION_JSON)
                .content(productAsString)).andExpect(status().isOk());

        Assert.assertTrue(viewed);
    }

    @Test
    public void testDeleteProduct() throws Exception {

        Mockito.doAnswer(invocationOnMock -> {
            Assert.assertEquals("123456789", invocationOnMock.getArguments()[0]);
            viewed = true;
            return null;
        }).when(productsRepository).delete(Mockito.anyString());

        mockMvc.perform(delete("/admin/product/123456789")).andExpect(status().isOk());

        Assert.assertTrue(viewed);
    }

    @Test
    public void testUpdateProduct() throws Exception {

        Mockito.doAnswer(invocationOnMock -> {
            Product product = new ObjectMapper().readValue((String) invocationOnMock.getArguments()[0], Product.class);
            Assert.assertEquals("wrong name", "name" , product.getName());
            viewed = true;
            return null;
        }).when(productsRepository).updateProduct(Mockito.anyString());

        Product product = new Product();
        product.setName("name");
        product.setProductType(ProductType.ALIMENTAIRE);

        mockMvc.perform(put("/admin/product/123456789") .contentType(MediaType.APPLICATION_JSON)
                                                        .content(new ObjectMapper().writeValueAsString(product)))
                                                        .andExpect(status().isOk());

        Assert.assertTrue(viewed);
    }


}
