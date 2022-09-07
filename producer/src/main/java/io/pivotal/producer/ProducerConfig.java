package io.pivotal.producer;

import io.pivotal.producer.game.Game;
import io.pivotal.producer.square.PylMessage;
import io.pivotal.producer.square.Square;
import io.pivotal.producer.square.SquareRepository;
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
    public Consumer<Square> acceptGameSquare(SquareRepository squareRepository) {
        return squareRepository::add;
    }

}
