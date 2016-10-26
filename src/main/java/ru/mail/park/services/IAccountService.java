package ru.mail.park.services;

import ru.mail.park.model.UserProfile;

/**
 * Created by Andry on 23.10.16.
 */
public interface IAccountService {

    public UserProfile addUser(String email, String password, String name);

    public UserProfile getUser(String email);

}
