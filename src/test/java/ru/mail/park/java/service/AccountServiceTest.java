package ru.mail.park.java.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.model.UserProfile;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Andry on 26.10.16.
 */

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @Autowired
    private JpaService accountService;

    @Test
    public void addUsers() {
        UserProfile user = accountService.addUser("jon@mail.ru", "12345", "jon");
        assertNotNull(user);

        user = accountService.addUser("miki@mail.ru", "12345", "miki");
        assertNotNull(user);

        user = accountService.addUser("jim@mail.ru", "12345", "jim");
        assertNotNull(user);
    }

    @Test
    public void getUserByEmail() {
        UserProfile user = accountService.getUser("jon@mail.ru");
        assertNotNull(user);
        assertEquals("jon@mail.ru", user.getEmail());
        assertEquals("12345", user.getPassword());
        assertEquals("jon", user.getName());
    }

    @Test
    public void getUsers() {
        List<UserProfile> users = accountService.getAllUsers();
        assertEquals(users.size(), 3);

        UserProfile user = accountService.getUser("jon@mail.ru");
        assertNotNull(user);
        assertEquals("jon@mail.ru", user.getEmail());

        user = accountService.getUser("miki@mail.ru");
        assertNotNull(user);
        assertEquals("miki@mail.ru", user.getEmail());

        user = accountService.getUser("jim@mail.ru");
        assertNotNull(user);
        assertEquals("jim@mail.ru", user.getEmail());

    }
}
