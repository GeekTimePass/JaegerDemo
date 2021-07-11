package com.jaeger.controller;

import com.jaeger.spring.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka/")
public class SpringKafkaController {

	@Autowired
    KafkaProducer kafkaSender;

	@GetMapping(value = "/producer/{message}")
	public String producer(@PathVariable("message") String message) throws Exception{
		kafkaSender.send("test1", message);
		return "Message sent to the Kafka Topic java_in_use_topic Successfully";
	}

}