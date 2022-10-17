package cs590.gatewayservice;

import cs590.gatewayservice.config.JwtConfig;
import cs590.gatewayservice.security.HttpRequestInterceptor;
import cs590.gatewayservice.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GatewayServiceApplication {
    @Autowired
    private JwtConfig config;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }

//        interceptors.add(new HttpRequestInterceptor("account_service_key", config.getAccountServiceKey()));
//        interceptors.add(new HttpRequestInterceptor("product_service_key", config.getProductServiceKey()));
//        interceptors.add(new HttpRequestInterceptor("order_service_key", config.getOrderServiceKey()));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
