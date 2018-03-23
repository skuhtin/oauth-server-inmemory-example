package ua.skuhtin.client.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface OAuth2Service {
    OAuth2AccessToken login(String login, String password);
    OAuth2AccessToken refresh(String refreshToken);
    ResponseEntity<HttpStatus> logOut(String token);
}
