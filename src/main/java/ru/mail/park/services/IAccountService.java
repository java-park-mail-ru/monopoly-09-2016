package ru.mail.park.services;

import ru.mail.park.exception.UserExistsException;
import ru.mail.park.model.UserProfile;

import java.util.List;

/**
 * Created by Andry on 23.10.16.
 */
public interface IAccountService {

    UserProfile addUser(String email, String password) throws UserExistsException;

    UserProfile getUser(String email);

    List<UserProfile> getAllUsers();

}
