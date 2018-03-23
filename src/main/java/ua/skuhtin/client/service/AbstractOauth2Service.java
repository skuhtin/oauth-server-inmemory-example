package ua.skuhtin.client.service;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public abstract class AbstractOauth2Service implements OAuth2Service {
    protected OAuth2AccessToken getToken(String login, String pass, OAuth2RestTemplate template) {
        OAuth2AccessToken token;
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resourceDetails = (ResourceOwnerPasswordResourceDetails) template.getResource();
        resourceDetails.setUsername(login);
        resourceDetails.setPassword(pass);

        token = provider.obtainAccessToken(resourceDetails, new DefaultAccessTokenRequest());
        return token;
    }

    protected OAuth2AccessToken refreshToken(String refreshToken, OAuth2RestTemplate template) {
        OAuth2AccessToken updatedToken;
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resourceDetails = (ResourceOwnerPasswordResourceDetails) template.getResource();
        DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(refreshToken);

        updatedToken = provider.refreshAccessToken(resourceDetails, token, new DefaultAccessTokenRequest());
        return updatedToken;
    }
}
