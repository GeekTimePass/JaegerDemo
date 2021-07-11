package com.jaeger.Jaeger;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.context.annotation.Bean;

public class JagerConfig {

    @Bean
    public JaegerTracer tracer(){
        return new io.jaegertracing.Configuration("Jaeger-Client")
                .withSampler(new Configuration.SamplerConfiguration()
                        .withType(ConstSampler.TYPE))
                .withReporter(new Configuration.ReporterConfiguration().withLogSpans(true))
                .getTracer();

    }
}
