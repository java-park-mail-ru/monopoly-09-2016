package ru.mail.park.java.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.model.UserProfile;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


/**
 * Created by Andry on 20.10.16.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RegistrationControllerTest {
    @Autowired
    private JpaService accountService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void init(){
        accountService.addUser("andy@mail", "12345", "andy");
    }

    @Test
    public void testLogin() {
        login();
    }

    private List<String> login() {
        HttpEntity requestEntity = new HttpEntity(new AuthRequest("andy@mail", "12345"));
        ResponseEntity<Response> loginResp = restTemplate.exchange("/api/session", HttpMethod.POST, requestEntity, Response.class);
        assertEquals(HttpStatus.OK, loginResp.getStatusCode());
        List<String> coockies = loginResp.getHeaders().get("Set-Cookie");
        assertNotNull(coockies);
        assertFalse(coockies.isEmpty());

        Response resp = loginResp.getBody();
        assertNotNull(resp);
        assertEquals("andy@mail", resp.getEmail());

        List<UserProfile> user = accountService.getAllUsers();

        return coockies;
    }


    @Test
    public void testMe() {
        List<String> coockies = login();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, coockies);
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        ResponseEntity<Response> meResp = restTemplate.exchange("/api/session", HttpMethod.GET, requestEntity, Response.class);

        assertEquals(HttpStatus.OK, meResp.getStatusCode());

        Response resp = meResp.getBody();
        assertNotNull(resp);
        assertEquals("andy@mail", resp.getEmail());

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
