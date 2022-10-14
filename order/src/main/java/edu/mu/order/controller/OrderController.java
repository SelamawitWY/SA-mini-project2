package edu.mu.order.controller;

import edu.mu.order.DTO.ClientPaymentRequest;
import edu.mu.order.entity.Order;
import edu.mu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getByID(@PathVariable Integer id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> save(@RequestBody  Order newOrder) throws Exception {
        Order order = orderService.placeOrder(newOrder);
        return ResponseEntity.ok().body(order);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> update(@RequestBody  Order newOrder) throws Exception {
        Order order = orderService.UpdateOrder(newOrder);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody ClientPaymentRequest request){
       String response =  orderService.checkout(request);
       return ResponseEntity.ok().body(response);
    }

    @GetMapping("/stockTest")
    public String checkout(){
        orderService.checkStock("message");
        return "sent";
    }
}
