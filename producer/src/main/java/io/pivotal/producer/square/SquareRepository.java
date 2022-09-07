package io.pivotal.producer.square;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SquareRepository {

    private final Map<UUID,List<Square>> squareMap = new HashMap<>();

    public void add(Square square) {
        List<Square> gameSquares = squareMap.getOrDefault(square.getGameId(), new ArrayList<>());
        gameSquares.add(square);
        squareMap.put(square.getGameId(), gameSquares);
    }

    public List<Square> getGameSquares(UUID gameId) {
        return squareMap.getOrDefault(gameId, Collections.emptyList());
    }

    public void removeGameSquares(UUID gameId) {
        squareMap.remove(gameId);
    }
}
