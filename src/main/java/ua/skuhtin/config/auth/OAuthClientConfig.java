package ua.skuhtin.config.auth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import ua.skuhtin.config.providers.OauthClientCredentials;

import java.util.Arrays;
import java.util.Objects;

@Configuration
@EnableOAuth2Client
public class OAuthClientConfig implements InitializingBean {

    @Autowired(required = false)
    private OauthClientCredentials clientCredentials;

    @Value("${oauth2.server}")
    private String oauthServer;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(this.clientCredentials)){
            throw new RuntimeException();
        }
    }

    @Bean
    public ResourceOwnerPasswordResourceDetails oauthResourceDetails() {
        return createDetails(clientCredentials);
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext clientContext) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(this.oauthResourceDetails(), clientContext);
        template.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        return template;
    }

    private ClientResourceOwnerPasswordResourceDetails createDetails(OauthClientCredentials credentials) {
        ClientResourceOwnerPasswordResourceDetails details = new ClientResourceOwnerPasswordResourceDetails();
        details.setId(credentials.getClientId());
        details.setClientId(credentials.getClientId());
        details.setClientSecret(credentials.getClientSecret());
        details.setGrantType("password");
        details.setAuthenticationScheme(AuthenticationScheme.form);
        details.setAccessTokenUri(oauthServer + "/oauth/token");
        return details;
    }

    private final class ClientResourceOwnerPasswordResourceDetails extends ResourceOwnerPasswordResourceDetails {
        private ClientResourceOwnerPasswordResourceDetails() {
        }
    }
}
