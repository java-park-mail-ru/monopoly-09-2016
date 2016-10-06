package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.AccountService;

import javax.servlet.http.HttpSession;

/**
 * Created by Andry on 27.09.16.
 */
@RestController
public class RegistrationController {
    private final AccountService accountService;

    @Autowired
    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }


    @RequestMapping(path = "/api/user", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody RegistrationRequest body) {

        final String name = body.getName();
        final String password = body.getPassword();
        final String email = body.getEmail();
        if (StringUtils.isEmpty(name)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Some fields is invalid"));
        }
        final UserProfile existingUser = accountService.getUser(email);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email already exist"));
        }

        accountService.addUser(email, password, name);
        return ResponseEntity.ok(new SuccessResponse(email));
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.GET)
    public ResponseEntity createSession(HttpSession httpSession) {

        String email = httpSession.getAttribute("email").toString();
        if(StringUtils.isEmpty(email))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Session is invalid"));

        UserProfile user = accountService.getUser(email);

        if(user != null)
            return ResponseEntity.ok(new SuccessResponse(email));

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
        final String email = body.getEmail();

        if (StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email or password is incorrect"));
        }
        final UserProfile user = accountService.getUser(email);

        if ( user != null && user.getPassword().equals(password)) {

            httpSession.setAttribute("email", email);
            return ResponseEntity.ok(new SuccessResponse(user.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email or password is incorrect"));
    }

    private static final class RegistrationRequest {
        private String email;
        private String name;
        private String password;

        @SuppressWarnings("unused")
        public RegistrationRequest () {}

        @SuppressWarnings("unused")
        public RegistrationRequest(String email, String name, String password) {
            this.email = email;
            this.name = name;
            this.password = password;

        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

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

    private static final class SuccessResponse {
        private String email;

        private SuccessResponse(String email) {
            this.email = email;
        }

        @SuppressWarnings("unused")
        public String getEmail() {
            return email;
        }
    }

    private static final class ErrorResponse {
        private String message;

        private ErrorResponse(String message){ this.message = message; }

        @SuppressWarnings("unused")
        public String getMessage() {
            return message;
        }
    }

}



