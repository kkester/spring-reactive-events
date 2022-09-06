package io.pivotal.producer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {

    private final Consumer<Game> publishMessage;
    private final SquareQueue squareQueue;

    public Mono<Game> playGame(Game game) {
        return Mono.fromCallable(() -> establishGame(game))
                .doOnSuccess(publishMessage)
                .map(this::accumulateSquares)
                .map(this::applySquareSelection);
    }

    private Game establishGame(Game game) {
        UUID gameId = UUID.randomUUID();
        return game.toBuilder().gameId(gameId).build();
    }

    @SneakyThrows
    private Game accumulateSquares(Game game) {
        LocalDateTime timeout = LocalDateTime.now().plusSeconds(5);
        while (squareQueue.getGameSquares(game.getGameId()).size() < 10 && LocalDateTime.now().isBefore(timeout)) {
            log.info("Found squares {}", squareQueue.getGameSquares(game.getGameId()).size());
        }

        List<Square> gameSquares = squareQueue.getGameSquares(game.getGameId()).stream()
                .map(s -> Square.builder().value(s.getValue()).build())
                .collect(Collectors.toList());
        squareQueue.removeGameSquares(game.getGameId());
        return gameSquares.size() == 10 ? game.toBuilder().squares(gameSquares).build() : game;
    }

    private Game applySquareSelection(Game game) {
        if (game.getSquares().isEmpty()) {
            return game;
        }

        int selectedSquareIndex = new Random().nextInt(10);
        List<Square> gameSquares = game.getSquares();
        Square selectedSquare = gameSquares.get(selectedSquareIndex);
        selectedSquare.setSelected(true);

        Game result;
        if (SquareValue.WHAMMY.equals(selectedSquare.getValue())) {
            result = game.toBuilder()
                    .gameId(null)
                    .lives(game.getLives() - 1)
                    .score(0)
                    .totalScore(0)
                    .build();
        } else {
            result = game.toBuilder()
                    .gameId(null)
                    .score(selectedSquare.getValue().score())
                    .totalScore(game.getTotalScore() + selectedSquare.getValue().score())
                    .build();
        }
        return result;
    }
}
