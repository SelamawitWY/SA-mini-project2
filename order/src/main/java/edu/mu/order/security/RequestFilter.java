package edu.mu.order.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestFilter implements HandlerInterceptor {
    private final String my_service_key;
    private JwtUtil jwtUtil;
    public RequestFilter(@Value("${service_key}") String key, JwtUtil jwtUtil) {
        this.my_service_key = key;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secret = request.getHeader("order_service_key");
        String token = request.getHeader("jwt");

        Boolean status = jwtUtil.validateToken(token);
        status = secret != null ? status || secret.trim().equals(my_service_key.trim()) : status;

        return status;//secret.equals(my_service_key);
    }


}
