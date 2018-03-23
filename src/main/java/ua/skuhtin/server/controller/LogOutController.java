package ua.skuhtin.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.skuhtin.server.service.LogOutService;

@RestController
public class LogOutController {

    @Autowired
    private LogOutService logOutService;

    @RequestMapping(value = "/oauth/logout/{token}", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> logOut(@PathVariable("token") String token) {
        logOutService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
