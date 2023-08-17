package com.kafka.integration.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    /**
     * sending messages
     * @param message
     */
    public void sendMessage(String message){
        kafkaTemplate.send(topic,message);
    }

}
