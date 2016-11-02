package ru.mail.park.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exception.UserExistsException;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.model.UserProfile;
import ru.mail.park.responses.AuthRequest;
import ru.mail.park.responses.ErrorResponse;
import ru.mail.park.responses.RegistrationRequest;
import ru.mail.park.responses.SuccessResponse;
import ru.mail.park.services.IAccountService;

import javax.servlet.http.HttpSession;

/**
 * Created by Andry on 27.09.16.
 */
@RestController
public class RegistrationController {
    private final IAccountService accountService;

    @Autowired
    public RegistrationController(JpaService jpaService) {
        this.accountService = jpaService;
    }


    @RequestMapping(path = "/api/user", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody RegistrationRequest body) {

        final String username = body.getUsername();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Some fields is invalid"));
        }

        try{
            accountService.addUser(username, password);
        }catch (UserExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email already exist"));
        }

        return ResponseEntity.ok(new SuccessResponse(username));
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.GET)
    public ResponseEntity createSession(HttpSession httpSession) {

        String username = httpSession.getAttribute("username").toString();
        if (StringUtils.isEmpty(username))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Session is invalid"));

        UserProfile user = accountService.getUser(username);

        if (user != null) return ResponseEntity.ok(new SuccessResponse(username));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Session is invalid"));
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.DELETE)
    public ResponseEntity deleteSession(HttpSession httpSession) {

        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthRequest body, HttpSession httpSession) {

        final String password = body.getPassword();
        final String username = body.getUsername();

        if (StringUtils.isEmpty(password)
                || StringUtils.isEmpty(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email or password is incorrect"));
        }
        final UserProfile user = accountService.getUser(username);

        if (user != null && user.getPassword().equals(password)) {

            httpSession.setAttribute("username", username);
            return ResponseEntity.ok(new SuccessResponse(user.getUsername()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email or password is incorrect"));
    }

}



