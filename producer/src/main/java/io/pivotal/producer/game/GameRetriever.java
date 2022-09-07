package io.pivotal.producer.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameRetriever {
    private final GameRepository gameRepository;

    public Mono<Game> retrieveGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .map(this::convert);
    }

    private Game convert(GameEntity gameEntity) {
        return Game.builder()
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .build();
    }
}
