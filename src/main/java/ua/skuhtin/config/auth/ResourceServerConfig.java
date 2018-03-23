package ua.skuhtin.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import ua.skuhtin.config.providers.OauthClientCredentials;

import java.util.Objects;

@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${oauth.resource.id}")
    private String resourceId;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth2.server}")
    private String oauthServer;

    @Autowired
    @Qualifier("authenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Bean
    public OauthClientCredentials clientCredentials() {
        return new OauthClientCredentials(clientId, clientSecret);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }

    @Bean(name = "tokenServices")
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setClientId(clientId);
        tokenServices.setCheckTokenEndpointUrl(oauthServer + "/oauth/check_token");
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs/**", "/webjars/**", "/oauth/logout**").permitAll()
                .antMatchers(HttpMethod.POST, "/user", "/user/login", "/user/refresh").permitAll()
                .antMatchers(HttpMethod.GET, "/hello").permitAll()
                .antMatchers("/secure**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}
