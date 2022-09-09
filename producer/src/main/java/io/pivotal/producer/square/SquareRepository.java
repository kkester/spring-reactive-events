package io.pivotal.producer.square;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SquareRepository {

    private final Map<UUID, List<Square>> squareMap = new HashMap<>();

    public void add(SquareMessage squareMessage) {
        List<Square> gameSquares = squareMap.getOrDefault(squareMessage.getGameId(), new ArrayList<>());
        gameSquares.add(Square.builder()
                .value(SquareValue.valueOf(squareMessage.getValue()))
                .build());
        squareMap.put(squareMessage.getGameId(), gameSquares);
    }

    public List<Square> getGameSquares(UUID gameId) {
        return squareMap.getOrDefault(gameId, Collections.emptyList());
    }

    public void removeGameSquares(UUID gameId) {
        squareMap.remove(gameId);
    }
}
