package com.example.stockservice.stockController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/stock")
public class StockController {
    @PostMapping("/message")
    public ResponseEntity<String>stockThreshold(@RequestBody String message){
        log.info(message);
        return ResponseEntity.ok("The product is below threshold, Please add more!");
    }
}