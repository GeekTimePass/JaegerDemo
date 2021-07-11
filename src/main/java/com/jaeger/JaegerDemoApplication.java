package com.jaeger;

import io.opentracing.contrib.spring.web.client.TracingRestTemplateInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
@EnableAutoConfiguration

public class JaegerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaegerDemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
//		restTemplate.setInterceptors(Collections.singletonList(new TracingRestTemplateInterceptor()));
		return restTemplate;
	}
}