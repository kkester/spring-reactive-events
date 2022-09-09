package io.pivotal.producer;

import io.pivotal.producer.game.Game;
import io.pivotal.producer.game.GameConverter;
import io.pivotal.producer.game.GameEntity;
import io.pivotal.producer.game.GameRepository;
import io.pivotal.producer.square.Square;
import io.pivotal.producer.square.SquareAccumulator;
import io.pivotal.producer.square.SquareValue;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PylService {

    private final GameRepository gameRepository;
    private final SquareAccumulator squareAccumulator;
    private final GameConverter gameConverter;

    @Data
    @Builder(toBuilder = true)
    private static class GamePlay {
        private GameEntity gameEntity;
        private List<Square> squares;
        private Square selectedSquare;
    }

    public Mono<Game> playGame(UriComponentsBuilder uriBuilder, UUID gameId) {
        return gameRepository.findById(gameId)
                .map(this::acquireSquares)
                .map(this::applySquareSelection)
                .map(this::updateGameEntity)
                .map(gamePlay -> convertToGame(uriBuilder, gamePlay));
    }

    private GamePlay acquireSquares(GameEntity gameEntity) {
        return GamePlay.builder()
                .gameEntity(gameEntity)
                .squares(squareAccumulator.acquireSquares(gameEntity.getId()))
                .build();
    }

    private GamePlay applySquareSelection(GamePlay gamePlay) {
        List<Square> gameSquares = gamePlay.getSquares();
        if (gameSquares.isEmpty()) {
            return gamePlay;
        }

        int selectedSquareIndex = new Random().nextInt(10);
        Square selectedSquare = gameSquares.get(selectedSquareIndex);
        selectedSquare.setSelected(true);
        return gamePlay.toBuilder().selectedSquare(selectedSquare).build();
    }

    private GamePlay updateGameEntity(GamePlay gamePlay) {
        Square selectedSquare = gamePlay.getSelectedSquare();
        if (selectedSquare == null) {
            return gamePlay;
        }

        GameEntity gameEntity = gamePlay.getGameEntity();
        gameEntity.setSpins(gameEntity.getSpins() - 1);
        if (SquareValue.WHAMMY.equals(selectedSquare.getValue())) {
            gameEntity.setTotalScore(0);
        } else {
            gameEntity.setTotalScore(gameEntity.getTotalScore() + selectedSquare.getValue().score());
            gameEntity.setSpins(gameEntity.getSpins() + selectedSquare.getValue().spins());
        }
        gameRepository.save(gameEntity).subscribe();
        return gamePlay;
    }

    private Game convertToGame(UriComponentsBuilder uriBuilder, GamePlay gamePlay) {
        GameEntity gameEntity = gamePlay.gameEntity;
        Square selectedSquare = gamePlay.getSelectedSquare();
        return selectedSquare == null ?
                gameConverter.convert(uriBuilder, gameEntity) :
                gameConverter.convert(uriBuilder, gameEntity, gamePlay.getSquares(), selectedSquare);
    }
}
