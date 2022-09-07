package io.pivotal.producer.square;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SquareAccumulator {

    private final Consumer<PylMessage> publishPylMessage;
    private final SquareRepository squareRepository;

    public List<Square> acquireSquares(UUID gameId) {
        publishPylMessage.accept(createPylMessage(gameId));
        LocalDateTime timeout = LocalDateTime.now().plusSeconds(5);
        return accumulateSquares(gameId, timeout);
    }

    private PylMessage createPylMessage(UUID gameId) {
        return PylMessage.builder().gameId(gameId).build();
    }

    @SneakyThrows
    private List<Square> accumulateSquares(UUID gameId, LocalDateTime timeout) {
        if (squareRepository.getGameSquares(gameId).size() < 10 && LocalDateTime.now().isBefore(timeout)) {
            Thread.sleep(50);
            return accumulateSquares(gameId, timeout);
        }

        List<Square> gameSquares = squareRepository.getGameSquares(gameId).stream()
                .map(s -> Square.builder().value(s.getValue()).build())
                .collect(Collectors.toList());
        squareRepository.removeGameSquares(gameId);
        return gameSquares.size() == 10 ? gameSquares : Collections.emptyList();
    }

}
