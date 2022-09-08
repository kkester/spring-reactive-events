package io.pivotal.producer.game;

import io.pivotal.producer.square.Square;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class GameConverter {

    public Game convert(UriComponentsBuilder uriBuilder, GameEntity gameEntity) {
        URI playUri = getPlayUri(uriBuilder, gameEntity);
        return Game.builder()
                .play(playUri)
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .build();
    }

    public Game convert(UriComponentsBuilder uriBuilder, GameEntity gameEntity, List<Square> squares, Square selectedSquare) {
        URI playUri = getPlayUri(uriBuilder, gameEntity);
        return Game.builder()
                .play(playUri)
                .score(selectedSquare.getValue().score())
                .spins(gameEntity.getSpins())
                .totalScore(gameEntity.getTotalScore())
                .squares(squares)
                .build();
    }

    private URI getPlayUri(UriComponentsBuilder uriBuilder, GameEntity gameEntity) {
        return gameEntity.getSpins() > 0 ?
                uriBuilder.pathSegment("pyl","games","{0}").build(gameEntity.getId()) :
                uriBuilder.pathSegment("pyl","games","{0}").build("new-game");
    }
}
