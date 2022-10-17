package miu.cs590.stockservice.controller;

import miu.cs590.stockservice.service.KafKaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController
{
    private final KafKaProducer producerService;

    @Autowired
    public KafkaProducerController(KafKaProducer producerService)
    {
        this.producerService = producerService;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message)
    {
        this.producerService.sendMessage(message);
    }
}