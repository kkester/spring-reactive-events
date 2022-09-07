package io.pivotal.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        IntStream.range(0, 10).forEach(i -> {
            Square square = Square.builder()
                    .gameId(pylMessage.getGameId())
                    .value(SquareValue.values()[random.nextInt(SquareValue.values().length)])
                    .build();
            publishMessage.accept(square);
        });
    }
}
