package ua.skuhtin.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import ua.skuhtin.client.service.OAuth2Service;
import ua.skuhtin.dto.LoginDto;

@RestController
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private OAuth2Service oAuth2Service;

    @ApiOperation(value = "login controller")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> getToken(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(oAuth2Service.login(loginDto.getLogin(), loginDto.getPassword()), HttpStatus.OK);
    }

    @ApiOperation(value = "logout controller")
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> logOut() {
        OAuth2AuthenticationDetails authentication = (OAuth2AuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.info("Start for token: " + authentication.getTokenValue());
        return oAuth2Service.logOut(authentication.getTokenValue());
    }

    @ApiOperation(value = "refresh token")
    @RequestMapping(value = "/user/refresh", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return new ResponseEntity<>(oAuth2Service.refresh(refreshToken), HttpStatus.OK);
    }
}
