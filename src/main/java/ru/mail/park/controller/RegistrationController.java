package ru.mail.park.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exception.ServiceException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

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

        try {
            UserProfile user = accountService.addUser(username, password);
            UserProfile newUser = accountService.getUser(username);

            if (user == null || newUser == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Some fields is invalid"));

            if (user.getUsername().equals(newUser.getUsername()) && user.getUsername().equals(newUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Some fields is invalid"));
            }
        } catch (UserExistsException e) {
            LOGGER.error(String.format("User %s already exists", username), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (ServiceException e) {
            LOGGER.error("Database Error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

        LOGGER.info(String.format("User %s is create", username));
        return ResponseEntity.ok(new SuccessResponse(username));
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.GET)
    public ResponseEntity checkSession(HttpSession httpSession) {

        Object attribute = httpSession.getAttribute("username");
        if (attribute == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Session is invalid"));

        String username = attribute.toString();
        UserProfile user = accountService.getUser(username);

        if (user != null) {
            LOGGER.info(String.format("User %s check his session", username));
            return ResponseEntity.ok(new SuccessResponse(username));
        }

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
            LOGGER.info(String.format("User %s logged on to the service", username));
            httpSession.setAttribute("username", username);
            return ResponseEntity.ok(new SuccessResponse(user.getUsername()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email or password is incorrect"));
    }
}



