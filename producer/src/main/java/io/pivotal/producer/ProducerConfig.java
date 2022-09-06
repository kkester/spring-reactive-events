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

    private final Sinks.Many<Game> sink = Sinks.many().multicast().onBackpressureBuffer();

//    @Bean
//    public Supplier<LocalDateTime> publishDate() {
//        return () -> {
//            log.info("Publishing Date");
//            return LocalDateTime.now();
//        };
//    }

    @Bean
    public Consumer<Game> publishGame() {
        return game -> sink.emitNext(game, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Bean
    public Supplier<Flux<Game>> playGame() {
        return sink::asFlux;
    }

    @Bean
    public Consumer<Square> acceptGameSquare(SquareQueue squareQueue) {
        return squareQueue::add;
    }

}
