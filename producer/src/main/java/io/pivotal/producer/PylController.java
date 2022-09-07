package io.pivotal.producer;

import io.pivotal.producer.game.Game;
import io.pivotal.producer.game.GameInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PylController {

    private final GameInitializer gameInitializer;
    private final PylService pylService;

    @GetMapping("/pyl/games/new-game")
    public Mono<Game> newGame() {
        return gameInitializer.newGame();
    }

    @GetMapping("/pyl/games/{gameId}")
    public Mono<Game> play(@PathVariable UUID gameId) {
        return pylService.playGame(gameId);
    }
 }
