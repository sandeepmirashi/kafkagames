package com.mirashitech.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirashitech.dto.Customer;
import com.mirashitech.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class KafkaMessageListener {
    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

//    @KafkaListener(topics = "mirashitech-testlag", groupId = "mt-consumergrouptestlag")
//    public void consume1(String message){
//        log.info("Consumer 1 : {}", message);
//    }
//
//    @KafkaListener(topics = "mirashitech-testlag", groupId = "mt-consumergrouptestlag")
//    public void consume2(String message){
//        log.info("Consumer 2 : {}", message);
//    }
//
//    @KafkaListener(topics = "mirashitech-testlag", groupId = "mt-consumergrouptestlag")
//    public void consume3(String message){
//        log.info("Consumer 3: {}", message);
//    }
//
//    @KafkaListener(topics = "mirashitech-testlag", groupId = "mt-consumergrouptestlag")
//    public void consumebkup(String message){
//        log.info("Consumer 4 or bkup: {}", message);
//    }

//    @KafkaListener(topics = "mirashitech-topic", groupId = "mt-consumergroup-object")
//    public void consume(Customer customer){
//        log.info("Consumer : {}", customer);
//    }

//    @KafkaListener(topics = "mirashitech-topic1", groupId = "mt-consumergroup-object",
//            topicPartitions = {@TopicPartition(topic = "mirashitech-topic1", partitions = {"2"})})
//    public void consume(String message){
//        log.info("Consumer : {}", message);
//    }

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5,maxDelay = 15000),
        exclude = {NullPointerException.class}) //, RuntimeException.class
    @KafkaListener(topics = "${app.topic.name}", groupId = "mt-consumergroup-user")
    public void consumeEvents(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(user),
                    topic, offset);
            List<String> restrictedips = Stream.of("123.123.123.123").collect(Collectors.toList());
            if (restrictedips.contains(user.getIpAddress())){
                throw new RuntimeException("Invalid IP Address received!");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @DltHandler
    public void listenDLT(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset){
        log.info("DLT Received :{}, from {}, offset {}", user.getFirstName(), topic, offset);
    }

}
