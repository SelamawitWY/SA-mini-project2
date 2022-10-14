//package edu.miu.paypal.aspect;
//
//
//import edu.miu.paypal.exception.UnknownServiceException;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Aspect
//@Component
//public class FilterAspect {
//    @Value("${mytoken}")
//    private String token;
//    @Autowired
//    HttpServletRequest request;
//
//    @Pointcut("execution(* edu.miu.paypal.controller.*.*(..))")
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
//
//
//}
