package ru.mail.park.java.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.exception.UserExistsException;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.model.UserProfile;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by Andry on 26.10.16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class AccountServiceTest {

    @Autowired
    private JpaService accountService;

    @Before
    public void init() {
        try {
            accountService.addUser("jon", "12345");
            accountService.addUser("miki", "12345");
            accountService.addUser("jim", "12345");
        }catch (UserExistsException e)
        {
            assertNotNull(null);
        }
    }

    @Test
    public void addUsers() {

        try {
            UserProfile user = accountService.addUser("fill", "12345");
            assertNotNull(user);
        }catch (UserExistsException e) {
            assertNotNull(null);
        }
    }

    @Test(expected = UserExistsException.class)
    public void addExistsUsers() {
        accountService.addUser("jon", "12345"); // this user exists
    }

    @Test
    public void getUserByUsername() {
        UserProfile user = accountService.getUser("jon");
        assertNotNull(user);
        assertEquals("jon", user.getUsername());
        assertEquals("12345", user.getPassword());
    }

    @Test
    public void getNotExistsUserByUsername() {
        UserProfile user = accountService.getUser("vova");
        assertNull(user);
    }

    @Test
    public void getUsers() {
        List<UserProfile> users = accountService.getAllUsers();
        assertEquals(users.size(), 3);

        assertEquals("jon", users.get(0).getUsername());
        assertEquals("miki", users.get(1).getUsername());
        assertEquals("jim", users.get(2).getUsername());

    }
}
