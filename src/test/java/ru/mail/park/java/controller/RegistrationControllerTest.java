package ru.mail.park.java.controller;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.model.UserProfile;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


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

    //@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Before
    public void init(){
        accountService.addUser("andy@mail", "12345", "andy");
        accountService.addUser("jim@mail", "12345", "jim");
        accountService.addUser("mikki@mail", "12345", "mikki");

        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();
    }



    @Test
    public void testLogin() throws Exception{
        login();
    }

    private List<String> login() throws Exception{

        int cookie = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/session")
                .content("{\"email\": \"andy@mail\", \"password\": \"12345\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse().getCookies().length;

//        HttpEntity requestEntity = new HttpEntity(new AuthRequest("andy@mail", "12345"));
//        ResponseEntity<Response> loginResp = restTemplate.exchange("/api/session", HttpMethod.POST, requestEntity, Response.class);
//        assertEquals(HttpStatus.OK, loginResp.getStatusCode());
//        List<String> coockies = loginResp.getHeaders().get("Set-Cookie");
//        assertNotNull(coockies);
//        assertFalse(coockies.isEmpty());
//
//        Response resp = loginResp.getBody();
//        assertNotNull(resp);
//        assertEquals("andy@mail", resp.getEmail());

        List<UserProfile> user = accountService.getAllUsers();

        // return coockies;
        return new ArrayList<String>();
    }




    @Test
    public void testMe() {
//        List<String> coockies = login();
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.put(HttpHeaders.COOKIE, coockies);
//        HttpEntity requestEntity = new HttpEntity(requestHeaders);
//
//        ResponseEntity<Response> meResp = restTemplate.exchange("/api/session", HttpMethod.GET, requestEntity, Response.class);
//
//        assertEquals(HttpStatus.OK, meResp.getStatusCode());
//
//        Response resp = meResp.getBody();
//        assertNotNull(resp);
//        assertEquals("andy@mail", resp.getEmail());

        List<UserProfile> user = accountService.getAllUsers();
    }



    private static final class Response {
        private String email;

        @SuppressWarnings("unused")
        public Response(){}

        private Response(String email) {
            this.email = email;
        }

        @SuppressWarnings("unused")
        public String getEmail() {
            return email;
        }
    }

    private static final class AuthRequest {
        private String email;
        private String password;

        @SuppressWarnings("unused")
        public AuthRequest () {}

        @SuppressWarnings("unused")
        public AuthRequest(String email, String password) {
            this.email = email;
            this.password = password;

        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        @SuppressWarnings("unused")
        public void setPassword(String password) {
            this.password = password;
        }

        @SuppressWarnings("unused")
        public void setEmail(String email) {
            this.email = email;
        }
    }
}
