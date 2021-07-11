package com.jaeger.controller;

import com.google.common.collect.ImmutableMap;
import com.jaeger.spring.kafka.KafkaProducer;
import io.opentracing.Span;
import io.opentracing.contrib.tracerresolver.TracerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka/")
public class SpringKafkaController {

	@Autowired
    KafkaProducer kafkaSender;

	@GetMapping(value = "/producer/{message}")
	public String producer(@PathVariable("message") String message) throws Exception{
		Span span = TracerResolver.resolveTracer().activeSpan();
		try {
			span.setBaggageItem("baggage", "Mukesh Baggage");
			span.log("Preparing to send message to kafka..");
			kafkaSender.send("test1", message);
			span.log(ImmutableMap.of("event", "Kafka Sender", "message", message));
			span.log("Message Sent");
			return "Message sent to the Kafka Topic java_in_use_topic Successfully";
		} finally {
			span.finish();
		}
	}

}