package io.pivotal.producer.square;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class ProducerConfig {

    private final Sinks.Many<PylMessage> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public Consumer<PylMessage> publishPylMessage() {
        return message -> sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Bean
    public Supplier<Flux<PylMessage>> playGame() {
        return sink::asFlux;
    }

    @Bean
    public Consumer<SquareMessage> acceptGameSquare(SquareRepository squareRepository) {
        return squareRepository::add;
    }

}
