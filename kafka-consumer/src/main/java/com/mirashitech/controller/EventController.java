package com.mirashitech.controller;

import com.mirashitech.dto.User;
import com.mirashitech.publisher.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;


    @PostMapping("/publish")
    public  ResponseEntity<?>  publishUser(@RequestBody User user) {
        publisher.sendEvents(user);
        return ResponseEntity.ok("Message published successfully..");
    }


}
