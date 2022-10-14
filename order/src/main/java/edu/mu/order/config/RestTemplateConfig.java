package edu.mu.order.config;

import edu.mu.order.security.HttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Value("${SECRET_KEY}")
    private String secret_key;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }

        interceptors.add(new HttpRequestInterceptor("secret-key", secret_key));
        restTemplate.setInterceptors(interceptors);


        return restTemplate;
    }
}
