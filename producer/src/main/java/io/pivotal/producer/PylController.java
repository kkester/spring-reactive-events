package io.pivotal.producer;

import io.pivotal.producer.game.Game;
import io.pivotal.producer.game.GameInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PylController {

    private final GameInitializer gameInitializer;
    private final PylService pylService;

    @GetMapping("/pyl/games/new-game")
    public Mono<Game> newGame(UriComponentsBuilder uriBuilder) {
        return gameInitializer.newGame(uriBuilder);
    }

    @GetMapping("/pyl/games/{gameId}")
    public Mono<Game> play(@PathVariable UUID gameId, UriComponentsBuilder uriBuilder) {
        return pylService.playGame(uriBuilder, gameId);
    }
 }
