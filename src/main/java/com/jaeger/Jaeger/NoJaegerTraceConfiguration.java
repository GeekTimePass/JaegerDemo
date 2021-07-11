package com.jaeger.Jaeger;

import feign.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class NoJaegerTraceConfiguration {

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        return io.opentracing.noop.NoopTracerFactory.create();
    }

    /**
     * Trace id not propagated via the Feign client
     * If you are using Feign, in some cases it might be necessary to explicitely expose the Feign client in the Spring configuration,
     * in order to get the uber-trace-id propagated. This can be done easily by adding the following into one of your configuration classes:
     * @return
     */
    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}