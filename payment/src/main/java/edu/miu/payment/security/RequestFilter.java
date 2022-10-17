package edu.miu.payment.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class RequestFilter implements HandlerInterceptor {
    private final String myServiceKey;

    public RequestFilter(@Value("${service_key}") String key) {
        this.myServiceKey = key;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secret = request.getHeader("payment_service_key").trim();

        return secret.equals(myServiceKey.trim());
    }


}
