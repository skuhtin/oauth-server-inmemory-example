package ua.skuhtin.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private Boolean swaggerEnable;
    @Value("${api.name}")
    private String apiName;

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(newArrayList(generateBearerHeaderParameter()))
                .securitySchemes(singletonList(
                        new ApiKey("Authorization", "Bearer", "header")
                ))
                .enable(swaggerEnable)
                .ignoredParameterTypes(OAuth2Authentication.class)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .paths(Predicates.not(PathSelectors.regex("/oauth.*")))
                .build();
    }

    private Parameter generateBearerHeaderParameter() {
        return new ParameterBuilder()
                .name("Authorization")
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .description("Access Token ID")
                .defaultValue("Bearer ")
                .required(false).build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(apiName).build();
    }
}
