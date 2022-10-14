package miu.cs590.stockservice.service;

import miu.cs590.stockservice.model.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer
{
    private final Logger logger =
            LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = AppConstants.TOPIC_NAME,
            groupId = AppConstants.GROUP_ID)
    public void consume(String message)
    {
        logger.info(String.format("Message received -> %s", message));
    }
}