package ua.skuhtin.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ServiceImpl extends AbstractOauth2Service {

    @Autowired
    @Qualifier("oAuth2RestTemplate")
    private OAuth2RestTemplate oauthRestTemplate;

    @Value("${oauth2.server}")
    private String oauthServer;

    @Override
    public OAuth2AccessToken login(String login, String password) {
        try {
            return getToken(login, password, oauthRestTemplate);
        } catch (OAuth2AccessDeniedException e) {
            throw new OAuth2AccessDeniedException("wrong enter for login " + login);
        }
    }

    @Override
    public OAuth2AccessToken refresh(String refreshToken) {
        OAuth2AccessToken updateToken = refreshToken(refreshToken, oauthRestTemplate);
        return updateToken;
    }

    @Override
    public ResponseEntity<HttpStatus> logOut(String token) {
        ResponseEntity responseEntity =  oauthRestTemplate.getForEntity(oauthServer + "/oauth/logout/" + token, Object.class);
        return responseEntity;
    }
}
