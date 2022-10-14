package edu.mu.order.service;

import edu.mu.order.DTO.ClientPaymentRequest;
import edu.mu.order.DTO.PaymentRequest;
import edu.mu.order.DTO.ProductDto;
import edu.mu.order.entity.Order;
import edu.mu.order.entity.OrderItem;
import edu.mu.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @Value("${MIN_STOCK_SIZE}")
    private int stockSize;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private boolean checkAvailability(List<OrderItem> items){
       AtomicReference<Boolean> isAllProductAvail = new AtomicReference<>(true);

       items.stream().forEach(item -> {
            ProductDto product = restTemplate.getForObject("http://product-service:9091/products/getById/" + item.getProductId(), ProductDto.class);
            boolean isProdAvail = item.getQuantity() < product.getAvailableUnit();
            int availableUnit = product.getAvailableUnit();
            isAllProductAvail.set(isAllProductAvail.get() && isProdAvail);

            if(product.getAvailableUnit() < stockSize) {
                //call stock service here


                System.out.println("Product id "+ item.getProductId() + " is running out of stock, current = " +  availableUnit);
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
                String response = restTemplate.postForObject("http://stock-service:8181/stock/message", message, String.class);
            }

            restTemplate.put("http://product-service:9091/products/updateQuantity/" + item.getProductId() + '/'+ newQuantity, Boolean.class);
        });
    }

    public Order placeOrder(Order order) throws Exception {

        if(checkAvailability(order.getItems())){
             updateProduct(order.getItems());
            return orderRepository.save(order);
        }

        throw new Exception("Unavailable products");
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order findById(Integer id){
        return orderRepository.findById(id).get();
    }

    public String checkout(ClientPaymentRequest request ){
        Order order = orderRepository.findById(request.getOrderId()).get();
        final Double[] totalPrice = {0d};

        order.getItems().stream().forEach(item -> {
            ProductDto product = restTemplate.getForObject("http://product-service:9091/products/getById/" + item.getProductId(), ProductDto.class);
            totalPrice[0] = totalPrice[0] + (product.getPrice() * item.getQuantity()) ;
        });

        String accountId = httpServletRequest.getHeader("account-id");

        PaymentRequest paymentRequest = new PaymentRequest(request.getOrderId(), request.getPaymentType(), totalPrice[0], Integer.parseInt(accountId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(paymentRequest);

        HttpEntity<PaymentRequest> newRequest = new HttpEntity<>(paymentRequest, headers);
        String response = restTemplate.postForObject("http://payment-service:9092/", newRequest, String.class);

        return response.equals("") ?  "ordered" : response ;
    }

    public void checkStock(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> newRequest = new HttpEntity<>(message, headers);
        String response = restTemplate.postForObject("http://stock-service:9000/kafka/publish?message=hellofromstock", newRequest, String.class);
    }
}
