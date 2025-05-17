package com.mirashitech.config;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${app.topic.name}")
    private String topicName;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topicName, 3, (short) 1);
    }

    //Create a bean instead of yml
//    #spring:
//            #  kafka:
//            #    producer:
//            #      bootstrap-servers: localhost:9092
//            #      key-serializer: org.apache.kafka.common.serialization.StringSerializer
//#      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
//#      properties:
//            #        spring:
//            #          json:
//            #            trusted:
//            #              packages: com.mirashitech.dto
//instead below can be done
//    @Bean
//    public Map<String, Object> producerConfig(){
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory(){
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(){
//        return new KafkaTemplate<>(producerFactory());
//    }
}
