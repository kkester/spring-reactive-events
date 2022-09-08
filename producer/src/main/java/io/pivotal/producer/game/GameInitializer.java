package io.pivotal.producer.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GameInitializer {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;

    public Mono<Game> newGame(UriComponentsBuilder uriBuilder) {
        GameEntity gameEntity = GameEntity.builder()
                .spins(5)
                .totalScore(0)
                .build();
        return gameRepository.save(gameEntity)
                .map(savedEntity -> gameConverter.convert(uriBuilder, savedEntity));
    }

}
