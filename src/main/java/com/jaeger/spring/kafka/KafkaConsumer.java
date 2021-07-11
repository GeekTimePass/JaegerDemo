package com.jaeger.spring.kafka;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "test1")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        Headers headers = consumerRecord.headers();
        for (Header header:headers) {
            System.out.println("Header Key = "+ header.key()+ "Header Value="+new String(header.value(), StandardCharsets.UTF_8).replace("\"",""));
        }
        setPayload(consumerRecord.toString());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }

    private void setPayload(String payload) {
        this.payload = payload;
    }

}