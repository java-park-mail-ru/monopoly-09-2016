package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.SessionService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

/**
 * Created by user on 27.09.16.
 */
@RestController
public class RegistrationController {
    private final AccountService accountService;
    private final SessionService sessionService;

    @Autowired
    public RegistrationController(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }



    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody RegistrationRequest body, HttpSession httpSession) {

        final String name = body.getName();
        final String password = body.getPassword();
        final String email = body.getEmail();
        if (StringUtils.isEmpty(name)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ Some fields is invalid }");
        }
        final UserProfile existingUser = accountService.getUser(email);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ Email is already in use! }");
        }

        accountService.addUser(email, password, name);
        return ResponseEntity.ok(new SuccessResponse(email));
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity createSession(HttpSession httpSession)
    {
        final String sessionId = httpSession.getId();
        System.out.println(sessionId);

        String email = sessionService.GetEmailByCookie(sessionId);

        if( email != null )
            return ResponseEntity.ok(new SuccessResponse(email));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{ Session is invalid }");
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(path = "/session", method = RequestMethod.DELETE)
    public ResponseEntity deleteSession(HttpSession httpSession)
    {
        final String sessionId = httpSession.getId();
        System.out.println(sessionId);

        sessionService.DeleteSession(sessionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }

    @CrossOrigin(allowCredentials = "true")
    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody LoginRequest body, HttpSession httpSession) {

        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ Email or password is incorrect }");
        }
        final UserProfile user = accountService.getUser(email);

        if ( user != null && user.getPassword().equals(password)) {

            sessionService.AddSession(httpSession.getId(), email);
            return ResponseEntity.ok(new SuccessResponse(user.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ Email or password is incorrect }");
    }

    private static final class RegistrationRequest {
        private String email;
        private String name;
        private String password;

        public RegistrationRequest () {}

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

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private static final class LoginRequest {
        private String email;
        private String password;

        public LoginRequest () {}

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;

        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

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

}



