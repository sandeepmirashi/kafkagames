package com.mirashitech.services;

import com.mirashitech.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("mirashitech-testlag", message);
//        future.get() dont use this which blocking
        future.whenComplete((result, exception) -> {
                    if (exception == null) {
                        System.out.println("Sent message = [" + message + "] with offset=" +
                                result.getRecordMetadata().offset());
                    } else {
                        System.out.println("Unable to send message = [ " + message + "] due to : " + exception.getMessage());
                    }
                }
        );

    }

    public void sendEventsToTopic(Customer customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("mirashitech-object", customer);
            future.whenComplete((result, exception) -> {
                        if (exception == null) {
                            System.out.println("Sent message = [" + customer.toString() + "] with offset=" +
                                    result.getRecordMetadata().offset());
                        } else {
                            System.out.println("Unable to send message = [ " + customer.toString() + "] due to : " + exception.getMessage());
                        }
                    }
            );
        } catch (Exception e) {
            System.out.println("ERROR : " +  e.getMessage());
        }

    }
}
