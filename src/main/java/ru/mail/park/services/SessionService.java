package ru.mail.park.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 28.09.16.
 */

@Service
public class SessionService {

    private Map<String,String> sessionCookieToUserEmail = new HashMap<String,String>();

    public void AddSession(String sessionId, String email){
        sessionCookieToUserEmail.put(sessionId, email);
    }

    public void DeleteSession(String sessionId){
        sessionCookieToUserEmail.remove(sessionId);
    }

    public String GetEmailByCookie(String sessionId) {
        return sessionCookieToUserEmail.get(sessionId);
    }
}
