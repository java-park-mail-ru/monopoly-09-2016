package ru.mail.park.services;

import ru.mail.park.exception.JpaException;
import ru.mail.park.exception.UserExistsException;
import ru.mail.park.model.UserProfile;

import java.util.List;

/**
 * Created by Andry on 23.10.16.
 */
public interface IAccountService {

    public UserProfile addUser(String email, String password) throws UserExistsException;

    public UserProfile getUser(String email);

    public List<UserProfile> getAllUsers();



}
