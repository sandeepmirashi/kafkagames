package com.mirashitech.services;

import com.mirashitech.dto.Customer;
import com.mirashitech.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;
    private String topicName;

    public void sendMessageToTopic(String message) {
//        CompletableFuture<SendResult<String, Object>> future = template.send("mirashitech-topic", 3, null, message);
////        future.get() dont use this which blocking
//        future.whenComplete((result, exception) -> {
//                    if (exception == null) {
//                        System.out.println("Sent message = [" + message + "] with offset=" +
//                                result.getRecordMetadata().offset());
//                    } else {
//                        System.out.println("Unable to send message = [ " + message + "] due to : " + exception.getMessage());
//                    }
//                }
//        );

        template.send("mirashitech-topic1", 3, null, "hi");
        template.send("mirashitech-topic1", 1, null, "hello");
        template.send("mirashitech-topic1", 2, null, "Sunday");
        template.send("mirashitech-topic1", 2, null, "Monday");
        template.send("mirashitech-topic1", 0, null, "Ola");

    }

    public void sendEventsToTopic(Customer customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("mirashitech-topic", customer);
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

    public void sendEvents(User user) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send(topicName, user);
            future.whenComplete((result, exception) -> {
                        if (exception == null) {
                            System.out.println("Sent message = [" + user.toString() + "] with offset=" +
                                    result.getRecordMetadata().offset());
                        } else {
                            System.out.println("Unable to send message = [ " + user.toString() + "] due to : " + exception.getMessage());
                        }
                    }
            );
        } catch (Exception e) {
            System.out.println("ERROR : " +  e.getMessage());
        }

    }
}
