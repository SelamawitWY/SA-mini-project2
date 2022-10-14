package cs590.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;
import java.util.Collections;

@Component
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cs590.accountservice"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Account service",
                "APIs for Account Service.",
                "1.0.0",
                "Terms of service",
                new Contact("Selamawit WY",
                        "https://www.linkedin.com/in/selamawit-yilma-a23a4240/",
                        "selam302@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }
}
