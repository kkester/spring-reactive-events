package io.pivotal.producer.game;

import io.pivotal.producer.square.Square;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameConverter {
    public Game convert(GameEntity gameEntity) {
        return Game.builder()
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .build();
    }

    public Game convert(GameEntity gameEntity, List<Square> squares, Square selectedSquare) {
        return Game.builder()
                .score(selectedSquare.getValue().score())
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .squares(squares)
                .build();
    }
}
