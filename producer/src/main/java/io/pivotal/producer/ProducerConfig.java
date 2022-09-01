package io.pivotal.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class ProducerConfig {

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

//    @Bean
//    public Supplier<LocalDateTime> publishDate() {
//        return () -> {
//            log.info("Publishing Date");
//            return LocalDateTime.now();
//        };
//    }

    @Bean
    public Consumer<String> publishMessage() {
        return d -> sink.emitNext(d, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Bean
    public Supplier<Flux<String>> publishDate() {
        return sink::asFlux;
    }

}
