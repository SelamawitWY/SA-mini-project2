package cs590.accountservice.security;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final String my_service_key;

    private JwtUtil jwtUtil;

    public RequestFilter(@Value("${SERVICE_KEY}") String key, JwtUtil jwtUtil) {
        this.my_service_key = key;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secret = request.getHeader("account_service_key");
        String token = request.getHeader("jwt");

        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements())
        {
            String headerName = headerNames.nextElement();
            System.out.println(headerName +"-----"+ request.getHeader(headerName));
        }

        System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/accounts/authenticate") ||
                request.getRequestURI().equals("/accounts/create")
        ) {
            return true;
        }

        System.out.println(jwtUtil.validateToken(token) + "==" + token +"====" + secret);
        Boolean status = jwtUtil.validateToken(token);

        status = secret != null ? status || secret.trim().equals(my_service_key.trim()) : status;

        return status; //jwtUtil.validateToken(token) && secret.equals(my_service_key);
    }


}
