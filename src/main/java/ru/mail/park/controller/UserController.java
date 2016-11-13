package ru.mail.park.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.jpa.JpaService;
import ru.mail.park.responses.RegistrationRequest;
import ru.mail.park.responses.SuccessResponse;
import ru.mail.park.services.IAccountService;

/**
 * Created by Andry on 03.11.16.
 */

@RestController
public class UserController {

    private final IAccountService accountService;

    @Autowired
    public UserController(JpaService jpaService) {
        this.accountService = jpaService;
    }


    @RequestMapping(path = "/api/rank", method = RequestMethod.GET)
    public ResponseEntity getRank() {
        return ResponseEntity.ok(new SuccessResponse("me"));
    }
}
