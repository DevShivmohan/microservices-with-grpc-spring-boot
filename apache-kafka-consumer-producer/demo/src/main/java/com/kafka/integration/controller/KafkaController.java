package com.kafka.integration.controller;

import com.kafka.integration.producer.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class KafkaController {
    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;
    @GetMapping
    public ResponseEntity<?> publishMessage(@RequestParam("message") String message){
        kafkaMessageProducer.sendMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body("Your message has published "+message);
    }
}
