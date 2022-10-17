package edu.miu.payment.service.impl;

import edu.miu.payment.model.*;
import edu.miu.payment.service.PaymentService;
import edu.miu.payment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JwtUtil jwtUtil;

    @Value("#{${paymentmap}}")
    private Map<String, String> way;


    @Value("${mytoken}")
    private String token;
    private HttpHeaders headers = new HttpHeaders();

    @Override
    public String checkout(OrderRequest orderRequest) {

        PaymentMethod paymentMethod;
        Integer accountId = orderRequest.getAccountId();
        Integer orderId = orderRequest.getOrderId();

        // Getting the payment information before sending to the payments

        if(!orderRequest.getPaymentType().isPresent()){
            // get payment information from account
            paymentMethod = restTemplate.getForObject("http://account-service:8081/accounts/preferredPaymentMethod/"+accountId,PaymentMethod.class);
        }else{
            //get payment information from account
            PaymentType paymentType1 = orderRequest.getPaymentType().get();
            paymentMethod = restTemplate.getForObject("http://account-service:8081/accounts/preferredPaymentMethod/"+ accountId + "/" + paymentType1,PaymentMethod.class);
        }

        System.out.println(paymentMethod);

        PaymentRequest paymentRequest = new PaymentRequest(accountId,orderId,paymentMethod);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> request =
                new HttpEntity<PaymentRequest>(paymentRequest, headers);


        if(paymentMethod == null){
            return "Payment type doesn't exist";
        }

        // Dynamically getting the url for the payment services from configMap
        String url = way.get(paymentMethod.getPaymentType().toString());
        System.out.println(url);
        String response = restTemplate.postForObject("http://"+ url,request,String.class);
        System.out.println(response);

        if(response != null){

            // getting the order from order service
            System.out.println("Order id " + orderId);
            Order order = restTemplate.getForObject("http://order-service:9097/orders/getById/"+ orderId, Order.class);
            order.setStatus("Payed");
            System.out.println("Getting order from order ");
            System.out.println(order);

           // Updating the order
            restTemplate.put("http://order-service:9097/orders/update",new HttpEntity<Order>(order,headers));
            System.out.println("order set to payed");
          // Send to shipping
            if(ship(accountId)){
                order.setStatus("shipped");
                restTemplate.put("http://order-service:9097/orders/update",new HttpEntity<Order>(order,headers));
                System.out.println("order set to shipped");
//                return "shipped";
            }

            return response;
        }

        return null;
    }

    private Boolean ship(Integer accountId){

        // Get shipping address by account id
        ShippingAddress shippingAddress = restTemplate.getForObject("http://account-service:8081/accounts/shippingAddress/"+accountId ,ShippingAddress.class);
        // Send to shipping

        HttpEntity<ShippingAddress> request =
                new HttpEntity<>(shippingAddress, headers);
        Boolean response = restTemplate.postForObject("http://shipping-service:9096",request,Boolean.class);

        return response;


    }


}
