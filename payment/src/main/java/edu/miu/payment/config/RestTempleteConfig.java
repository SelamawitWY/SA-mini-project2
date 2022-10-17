package edu.miu.payment.config;


import edu.miu.payment.security.HttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTempleteConfig {
    @Value("${mytoken}")
    private String token;

    @Value("${account_service_key}")
    private String accountServiceKey;

    @Value("${order_service_key}")
    private String orderServiceKey;

    @Value("${shipping_service_key}")
    private String shippingServiceKey;

    @Value("${bank_service_key}")
    private String bankServiceKey;

    @Value("${credit_service_key}")
    private String creditServiceKey;

    @Value("${paypal_servive_key}")
    private String payplaServiceKey;

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }

//        interceptors.add(new HttpRequestInterceptor("Secret-Key", token));
        interceptors.add(new HttpRequestInterceptor("account_service_key", accountServiceKey.trim()));
        interceptors.add(new HttpRequestInterceptor("order_service_key", orderServiceKey.trim()));
        interceptors.add(new HttpRequestInterceptor("shipping_service_key", shippingServiceKey.trim()));
        interceptors.add(new HttpRequestInterceptor("bank_service_key", bankServiceKey.trim()));
        interceptors.add(new HttpRequestInterceptor("credit_service_key", creditServiceKey.trim()));
        interceptors.add(new HttpRequestInterceptor("paypal_service_key", payplaServiceKey.trim()));

        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
