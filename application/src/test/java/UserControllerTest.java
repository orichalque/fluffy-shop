import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.UserRepository;
import com.alma.group8.application.util.MailVerifier;
import com.alma.group8.domain.exceptions.UserNotFoundException;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private MailVerifier mailVerifier;

    private MockMvc mockMvc;
    private boolean viewed;
    private ArrayList<User> users;

    @Before
    public void setUp() {
        viewed = false;
        users = new ArrayList<>();
        Mockito.when(mailVerifier.isValid(Mockito.anyString())).thenReturn(Boolean.TRUE);
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
        Mockito.doAnswer(invocationOnMock -> {
            User user = new ObjectMapper().readValue((String) invocationOnMock.getArguments()[0], User.class);
            Assert.assertEquals("mail3", user.getMail());
            Assert.assertEquals(Role.ADMIN, user.getRole());
            return null;
        }).when(userRepository).insert(Mockito.anyString());

        mockMvc.perform(post("/admin/user/admin").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().isOk());
    }

    @Test
    public void testPostClient() throws Exception {
        Mockito.doAnswer(invocationOnMock -> {
            User user = new ObjectMapper().readValue((String) invocationOnMock.getArguments()[0], User.class);
            Assert.assertEquals("mail3", user.getMail());
            Assert.assertEquals(Role.CLIENT, user.getRole());
            return null;
        }).when(userRepository).insert(Mockito.anyString());

        mockMvc.perform(post("/admin/user/client").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().isOk());
    }

    @Test
    public void testGetUserNok() throws Exception {
        Mockito.when(userRepository.find(Mockito.anyString())).thenThrow(new FunctionalException("Cannot find user"));

        mockMvc.perform(get("/admin/user/mail1"))
                .andExpect(status().is(404));
    }

    @Test
    public void testPostAdminNok() throws Exception {
        Mockito.doThrow(new FunctionalException("err")).when(userRepository).insert(Mockito.anyString());
        mockMvc.perform(post("/admin/user/admin").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().is(409));
    }

    @Test
    public void testPostClientNok() throws Exception {
        Mockito.doThrow(new FunctionalException("err")).when(userRepository).insert(Mockito.anyString());

        mockMvc.perform(post("/admin/user/client").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().is(409));
    }

    @Test
    public void testPostAdminWrongMailNok() throws Exception {
        Mockito.doReturn(false).when(mailVerifier).isValid(Mockito.anyString());

        mockMvc.perform(post("/admin/user/admin").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().is(400));
    }

    @Test
    public void testPostClientWrongMailNok() throws Exception {
        Mockito.doReturn(false).when(mailVerifier).isValid(Mockito.anyString());

        mockMvc.perform(post("/admin/user/client").contentType(MediaType.APPLICATION_JSON).content("mail3")).andExpect(status().is(400));
    }

    @Test
    public void testDeleteOk() throws Exception {
        Mockito.doAnswer(invocationOnMock -> {viewed = true; return null;}).when(userRepository).delete(Mockito.anyString());

        mockMvc.perform(delete("/admin/user/idToCheck")).andExpect(status().isOk());

        Assert.assertTrue(viewed);
    }

    @Test
    public void testDeleteNok() throws Exception {
        Mockito.doThrow(new UserNotFoundException("not found")).when(userRepository).delete(Mockito.anyString());

        mockMvc.perform(delete("/admin/user/idToCheck")).andExpect(status().is(404));
    }

}
