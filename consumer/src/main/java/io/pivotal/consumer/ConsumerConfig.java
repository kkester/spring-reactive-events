package io.pivotal.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class ConsumerConfig {

    private final Sinks.Many<Square> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public Consumer<Game> playGame(SquareService squareService) {
        return squareService::playGame;
    }

    @Bean
    public Consumer<Square> publishSquare() {
        return square -> sink.emitNext(square, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Bean
    public Supplier<Flux<Square>> gameSquareCreated() {
        return sink::asFlux;
    }

}
