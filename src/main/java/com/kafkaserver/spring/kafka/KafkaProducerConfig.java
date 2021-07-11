package com.kafkaserver.spring.kafka;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.contrib.kafka.spring.TracingKafkaAspect;
import io.opentracing.contrib.kafka.spring.TracingProducerFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Autowired
    private JaegerTracer jaegerTracer;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return new TracingProducerFactory<>(new DefaultKafkaProducerFactory<>(configProps),jaegerTracer);
    }

    //Use decorated ProduceFatory for KafkaTemplate
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //USe as aspect to decoreate @KakfaListner
    @Bean
    public TracingKafkaAspect tracingKafkaAspect(){
        return new TracingKafkaAspect(jaegerTracer);
    }
}
