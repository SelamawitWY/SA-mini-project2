package edu.mu.order.service;

import edu.mu.order.DTO.AppConstants;
import edu.mu.order.DTO.ClientPaymentRequest;
import edu.mu.order.DTO.PaymentRequest;
import edu.mu.order.DTO.ProductDto;
import edu.mu.order.entity.Order;
import edu.mu.order.entity.OrderItem;
import edu.mu.order.repository.OrderRepository;
import edu.mu.order.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @Value("${MIN_STOCK_SIZE}")
    private int stockSize;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    private boolean checkAvailability(List<OrderItem> items){
       AtomicReference<Boolean> isAllProductAvail = new AtomicReference<>(true);

       items.stream().forEach(item -> {
            ProductDto product = restTemplate.getForObject("http://product-service:9091/products/getById/" + item.getProductId(), ProductDto.class);
            if(product == null) {
                isAllProductAvail.set(false);
            }
            else {
                boolean isProdAvail = item.getQuantity() < product.getAvailableUnit();
                isAllProductAvail.set(isAllProductAvail.get() && isProdAvail);
            }
        });

       return isAllProductAvail.get();
    }

    private void updateProduct(List<OrderItem> items){
        items.stream().forEach(item -> {
            ProductDto product = restTemplate.getForObject("http://product-service:9091/products/getById/" + item.getProductId(), ProductDto.class);
            int newQuantity = product.getAvailableUnit() - item.getQuantity();

            if(newQuantity < stockSize) {
                String message = "Product id "+ item.getProductId() + " is running out of stock, current = " +  newQuantity;
                sendMessage(message);
                String response = restTemplate.postForObject("http://stock-service:8181/stock/message", message, String.class);
            }

            restTemplate.put("http://product-service:9091/products/updateQuantity/" + item.getProductId() + '/'+ newQuantity, Boolean.class);
        });
    }

    public Order UpdateOrder(Order order) {
        return orderRepository.save((order));
    }
    public Order placeOrder(Order order) throws Exception {

        if(checkAvailability(order.getItems())){
             updateProduct(order.getItems());
             order.setStatus("ordered");
            return orderRepository.save(order);
        }

        throw new Exception("Order includes unavailable products");
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order findById(Integer id){
        System.out.println(id);
        Order order =  orderRepository.findById(id).get();
        System.out.println(order);
        return  order;//orderRepository.findById(id).get();
    }

    public String checkout(ClientPaymentRequest request ){

        Optional<Order> optionalOrder = orderRepository.findById(request.getOrderId());
        Order order = optionalOrder.get();

        if(optionalOrder.isEmpty() ){
            return "Order doesn't not exist";
        } else if( !order.getStatus().equals("ordered")){
            return "Order is already paid or shipped";
        }

        final Double[] totalPrice = {0d};

        order.getItems().stream().forEach(item -> {
            ProductDto product = restTemplate.getForObject("http://product-service:9091/products/getById/" + item.getProductId(), ProductDto.class);
            totalPrice[0] = totalPrice[0] + (product.getPrice() * item.getQuantity()) ;
        });

        String accountId = jwtUtil.getUserName(httpServletRequest.getHeader("jwt"));

        PaymentRequest paymentRequest = new PaymentRequest(request.getOrderId(), request.getPaymentType(), totalPrice[0], Integer.parseInt(accountId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(paymentRequest);

        HttpEntity<PaymentRequest> newRequest = new HttpEntity<>(paymentRequest, headers);
        String response = restTemplate.postForObject("http://payment-service:9092/", newRequest, String.class);

        return response.equals("") ?  "ordered" : response ;
    }

    public void sendMessage(String message)
    {
        logger.info(String.format("Message sent -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
    }
}
