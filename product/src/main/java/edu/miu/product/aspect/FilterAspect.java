package edu.miu.product.aspect;

import edu.miu.product.exception.UnauthorizedUserException;
import edu.miu.product.exception.UnknownServiceException;
import edu.miu.product.util.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FilterAspect {
    @Value("${mytoken}")
    private String token;

    @Autowired
    HttpServletRequest request;
    @Autowired
    JwtUtil jwtUtil;

//    @Pointcut("execution(* edu.miu.product.controller.ProductController.delete(..)) || " +
//              "execution(* edu.miu.product.controller.ProductController.update(..))")
//    public void controllerClassMethods() {}
//
//    @Before("controllerClassMethods()")
//    public void tokenChecker(JoinPoint joinPoint){
//        if(request.getHeader("token") != null){
//            if (request.getHeader("token").equals(token)){
//                System.out.println("access granted");
//            }else {
//                System.out.println("access denied");
//                throw new UnknownServiceException("Access denied ");
//            }
//        }else {
//            System.out.println("no token");
//            throw new UnknownServiceException("Access denied");
//        }
//    }

//    @Before("controllerClassMethods()")
//    public void validateUser(JoinPoint joinPoint){
//        var authorizationHeader = request.getHeader("Authorization");
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String userToken = authorizationHeader.substring("Bearer ".length());
//            boolean isValid = jwtUtil.validateToken(userToken);
//
//            if(!isValid){
//                throw new UnauthorizedUserException("Unauthorized user");
//            }
//        }else{
//                throw new UnauthorizedUserException("Unauthorized user");
//        }
//

//        }
}
