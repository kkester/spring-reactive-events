package io.pivotal.producer;

import io.pivotal.producer.game.*;
import io.pivotal.producer.square.Square;
import io.pivotal.producer.square.SquareAccumulator;
import io.pivotal.producer.square.SquareValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PylService {

    private final GameRepository gameRepository;
    private final SquareAccumulator squareAccumulator;

    public Mono<Game> playGame(UUID gameId) {
        List<Square> squares = squareAccumulator.acquireSquares(gameId);
        return gameRepository.findById(gameId)
                .map(gameEntity -> applySquareSelection(gameEntity, squares));
    }

    private Game applySquareSelection(GameEntity gameEntity, List<Square> gameSquares) {
        if (gameSquares.isEmpty()) {
            return convert(gameEntity);
        }

        int selectedSquareIndex = new Random().nextInt(10);
        Square selectedSquare = gameSquares.get(selectedSquareIndex);
        selectedSquare.setSelected(true);

        gameEntity.setSpins(gameEntity.getSpins() - 1);
        if (SquareValue.WHAMMY.equals(selectedSquare.getValue())) {
            gameEntity.setTotalScore(0);
        } else {
            gameEntity.setTotalScore(gameEntity.getTotalScore() + selectedSquare.getValue().score());
        }
        gameRepository.save(gameEntity).subscribe();

        return Game.builder()
                .spins(gameEntity.getSpins())
                .score(selectedSquare.getValue().score())
                .totalScore(gameEntity.getTotalScore())
                .squares(gameSquares)
                .build();
    }

    private Game convert(GameEntity gameEntity) {
        return Game.builder()
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .build();
    }
}
