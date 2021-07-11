package com.jaeger.Jaeger;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.util.GlobalTracer;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

public class JagerConfig {

    @Autowired
    private Environment env;

    @Bean
    public JaegerTracer tracer() {
        return io.jaegertracing.Configuration.fromEnv(env.getProperty("spring.application.name"))
                .withSampler(
                        io.jaegertracing.Configuration.SamplerConfiguration.fromEnv()
                                .withType(ConstSampler.TYPE)
                                .withParam(1))
                .withReporter(
                        io.jaegertracing.Configuration.ReporterConfiguration.fromEnv()
                                .withLogSpans(true)
                                .withFlushInterval(1000)
                                .withMaxQueueSize(10000)
                                .withSender(
                                        io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
                                                .withAgentHost(env.getProperty("opentracing.jaeger.udp-sender.host"))
                                                .withAgentPort(Integer.valueOf(env.getProperty("opentracing.jaeger.udp-sender.port")))
                                ))
                .getTracer();
    }

    /**
     * The @PostConstruct above registers the Tracer as the GlobalTracer after initialization.
     * This is important because many of the tracing interceptors make use of the GlobalTracer behind the scenes whenever an instance
     * of a Tracer is not explicitly provided.
     */
    @PostConstruct
    public void registerToGlobalTracer() {
        if (!GlobalTracer.isRegistered()) {
            GlobalTracer.register(tracer());
        }
    }

   /* private void injectFirstKafkaConsumerSpan(String number, ProcessorContext context) {
        Tracer tracer = GlobalTracer.get();
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("receive")
                .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CONSUMER);

        Span span = spanBuilder.start();
        Tags.COMPONENT.set(span, TRACING_COMPONENT_NAME);
        Tags.PEER_SERVICE.set(span, TRACING_SERVICE_NAME);
        span.setTag("partition", context.partition());
        span.setTag("topic", context.topic());
        span.setTag("offset", context.offset());
        *//* add the number (aka the first consumer record value) as a span tag *//*
        span.setTag(TRACING_NUMBER_VALUE, number);
        span.finish();

        TextMap headersMapInjectAdapter = new TextMap() {
            @Override
            public Iterator<Map.Entry> iterator() {
                throw new UnsupportedOperationException("iterator should never be used with Tracer.inject()");
            }

            @Override
            public void put(String key, String value) {
                context.headers().add(key, value.getBytes(StandardCharsets.UTF_8));
            }
        };
        tracer.inject(span.context(), Format.Builtin.TEXT_MAP, headersMapInjectAdapter);
    }*/

}
