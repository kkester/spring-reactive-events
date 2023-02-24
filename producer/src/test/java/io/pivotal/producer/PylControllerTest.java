package io.pivotal.producer;

import io.pivotal.producer.game.Game;
import io.pivotal.producer.game.GameInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PylControllerTest {

    @Autowired
    PylController pylController;

    @MockBean
    PylService pylService;

    @MockBean
    GameInitializer gameInitializer;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(pylController).build();
    }

    @Test
    void newGame_givenValidRequest_returnsSuccessfulResponse() {
        UUID gameId = UUID.randomUUID();
        Game game = Game.builder().spins(5).score(1).totalScore(2).build();
        when(gameInitializer.newGame(any())).thenReturn(Mono.just(game));

        webTestClient.get()
            .uri("/pyl/games/new-game", gameId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .isEqualTo(game);
    }

    @Test
    void play_givenValidRequest_returnsSuccessfulResponse() {
        UUID gameId = UUID.randomUUID();
        Game game = Game.builder().spins(1).score(5).totalScore(10).build();
        when(pylService.playGame(any(), eq(gameId))).thenReturn(Mono.just(game));

        webTestClient.get()
            .uri("/pyl/games/{0}", gameId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .isEqualTo(game);
    }
}