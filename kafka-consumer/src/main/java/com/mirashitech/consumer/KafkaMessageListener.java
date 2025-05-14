package com.mirashitech.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {
    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "mirashitech-demo4", groupId = "mt-consumergroup")
    public void consume1(String message){
        log.info("Consumer 1 : {}", message);
    }

    @KafkaListener(topics = "mirashitech-demo4", groupId = "mt-consumergroup")
    public void consume2(String message){
        log.info("Consumer 2 : {}", message);
    }

    @KafkaListener(topics = "mirashitech-demo4", groupId = "mt-consumergroup")
    public void consume3(String message){
        log.info("Consumer 3: {}", message);
    }

    @KafkaListener(topics = "mirashitech-demo4", groupId = "mt-consumergroup")
    public void consumebkup(String message){
        log.info("Consumer 4 or bkup: {}", message);
    }
}
