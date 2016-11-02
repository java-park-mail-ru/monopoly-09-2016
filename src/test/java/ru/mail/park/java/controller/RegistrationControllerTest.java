package ru.mail.park.java.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.exception.UserExistsException;
import ru.mail.park.jpa.JpaService;
import org.springframework.http.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

/**
 * Created by Andry on 20.10.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class RegistrationControllerTest {

    @Autowired
    private JpaService accountService;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void init(){
        try {
            accountService.addUser("andy", "12345");
            accountService.addUser("jim", "12345");
            accountService.addUser("mikki", "12345");
        }catch (UserExistsException e){
            assertNotNull(null);
        }
    }



    @Test
    public void testLogin() throws Exception{
        login("andy", "12345");
    }

    private MockHttpSession login(String username, String password) throws Exception{

        MockHttpSession mockHttpSession = new MockHttpSession();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session")
                .session(mockHttpSession)
                .content(String.format("{\"username\":  \"%s\", \"password\": \"%s\" }", username, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(username, mockHttpSession.getAttribute("username"));

        return mockHttpSession;
    }


    @Test
    public void testMe() throws Exception{

        MockHttpSession mockHttpSession = login("andy", "12345");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void logout() throws Exception {
        MockHttpSession mockHttpSession = login("andy", "12345");

        assertEquals("andy", mockHttpSession.getAttribute("username"));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/session")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        assertEquals(mockHttpSession.isInvalid(), true);

    }

    @Test
    public void registration() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user")
                .content("{\"username\": \"bob\", \"password\": \"12345\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        login("bob", "12345");
    }


}
