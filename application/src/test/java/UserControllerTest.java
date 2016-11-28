import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.UserRepository;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

/**
 * Created by Thibault on 28/11/2016.
 * Test for the user controller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private ArrayList<User> users;

    @Before
    public void setUp() {
        users = new ArrayList<>();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user = new User();
        user.setMail("mail1");
        user.setRole(Role.ADMIN);
        users.add(user);

        user = new User();
        user.setMail("mail2");
        user.setRole(Role.CLIENT);
        users.add(user);

        Mockito.reset(userRepository);
    }

    @Test
    public void testGetUser() throws Exception {
        Mockito.when(userRepository.find(Mockito.anyString())).then(invocationOnMock -> new ObjectMapper().writeValueAsString(users.get(0)));
        mockMvc.perform(get("/admin/user/mail1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mail", is("mail1")))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    public void testGetUsers() throws Exception {
        Mockito.when(userRepository.findAll()).then(invocationOnMock -> users);
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testPostAdmin() throws Exception {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                User user = new ObjectMapper().readValue((String) invocationOnMock.getArguments()[0], User.class);
                Assert.assertEquals("mail3", user.getMail());
                Assert.assertEquals(Role.ADMIN, user.getRole());
                return null;
            }
        }).when(userRepository).insert(Mockito.anyString());

        mockMvc.perform(post("/admin/user/admin").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().isOk());
    }

    @Test
    public void testPostClient() throws Exception {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                User user = new ObjectMapper().readValue((String) invocationOnMock.getArguments()[0], User.class);
                Assert.assertEquals("mail3", user.getMail());
                Assert.assertEquals(Role.CLIENT, user.getRole());
                return null;
            }
        }).when(userRepository).insert(Mockito.anyString());

        mockMvc.perform(post("/admin/user/client").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().isOk());
    }
}
