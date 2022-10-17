package edu.miu.creditcard.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestFilter implements HandlerInterceptor {
    private final String my_service_key;

    public RequestFilter(@Value("${my_service_key}") String key) {
        this.my_service_key = key;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secret = request.getHeader("credit_service_key");

        return secret.trim().equals(my_service_key.trim());
    }
}
