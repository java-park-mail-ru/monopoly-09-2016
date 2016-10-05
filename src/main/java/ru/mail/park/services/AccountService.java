package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andry on 27.09.16.
 */

@Service
public class AccountService {
    private Map<String, UserProfile> userNameToUser = new HashMap<String, UserProfile>();

    public UserProfile addUser(String email, String password, String name) {
        final UserProfile userProfile = new UserProfile(email, password, name);
        userNameToUser.put(email, userProfile);
        return userProfile;
    }
    public UserProfile getUser(String email) {
        return userNameToUser.get(email);
    }
}
