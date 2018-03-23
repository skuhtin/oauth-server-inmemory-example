package ua.skuhtin.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LogOutServiceImpl implements LogOutService {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void logout(String token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        tokenStore.removeAccessToken(accessToken);
        if (Objects.nonNull(refreshToken)) {
            tokenStore.removeRefreshToken(refreshToken);
        }
    }
}
