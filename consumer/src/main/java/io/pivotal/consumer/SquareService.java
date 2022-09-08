package io.pivotal.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SquareService {
    private final Consumer<Square> publishMessage;

    public void playGame(PylMessage pylMessage) {
        log.info("Creating Squares for game {}", pylMessage);
        Random random = new Random();
        List<SquareValue> createdSquares = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            SquareValue squareValue = getValue(random, createdSquares);
            Square square = Square.builder()
                    .gameId(pylMessage.getGameId())
                    .value(squareValue)
                    .build();
            publishMessage.accept(square);
        });
    }

    private SquareValue getValue(Random random, List<SquareValue> createdSquares) {
        SquareValue squareValue = SquareValue.values()[random.nextInt(SquareValue.values().length)];
        if (SquareValue.FIVE_THOUSAND_PLUS_SPIN.equals(squareValue) && createdSquares.contains(SquareValue.FIVE_THOUSAND_PLUS_SPIN)) {
            return getValue(random, createdSquares);
        }
        createdSquares.add(squareValue);
        return squareValue;
    }
}
