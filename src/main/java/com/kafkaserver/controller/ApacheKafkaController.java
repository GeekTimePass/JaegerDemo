package com.kafkaserver.controller;

import com.kafkaserver.apache.kafka.MyKafkaConsumer;
import com.kafkaserver.apache.kafka.MyKakfaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApacheKafkaController {

    @Autowired
    private MyKakfaProducer kakfaProducer;

    @Autowired
    private MyKafkaConsumer kafkaConsumer;

    @RequestMapping("/produce")
    public String produce (){
        kakfaProducer.runProducer();
        return "Done";
    }

    @RequestMapping("/consume")
    public String consume (){
        kafkaConsumer.consume();
        return "Done";
    }

}
