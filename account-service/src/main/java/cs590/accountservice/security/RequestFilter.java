package cs590.accountservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestFilter implements HandlerInterceptor {
    private final String secret_key;

    public RequestFilter(@Value("${SECRET_KEY}") String key) {
        this.secret_key = key;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secret = request.getHeader("secret-key");

//        System.out.println(secret + " ===== " + secret_key);
//
//        Map<String, Object> returnValue = new HashMap<>();
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements())
//        {
//            String headerName = headerNames.nextElement();
//            System.out.println(headerName +"-----"+ request.getHeader(headerName));
//            returnValue.put(headerName, request.getHeader(headerName));
//        }

        return secret.equals(secret_key);
    }


}
