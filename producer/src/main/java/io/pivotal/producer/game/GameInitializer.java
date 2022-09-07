package io.pivotal.producer.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class GameInitializer {
    private final GameRepository gameRepository;

    public Mono<Game> newGame() {
        GameEntity gameEntity = GameEntity.builder()
//                .id(UUID.randomUUID())
                .spins(5)
                .totalScore(0)
                .build();
        return gameRepository.save(gameEntity)
                .map(this::convert);
    }

    private Game convert(GameEntity gameEntity) {
        return Game.builder()
                .play(URI.create("/pyl/games/" + gameEntity.getId()))
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .build();
    }
}
