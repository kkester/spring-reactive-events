package io.pivotal.producer.square;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return accumulateSquares(gameId);
    }

    private PylMessage createPylMessage(UUID gameId) {
        return PylMessage.builder().gameId(gameId).build();
    }

    @SneakyThrows
    private List<Square> accumulateSquares(UUID gameId) {
        LocalDateTime timeout = LocalDateTime.now().plusSeconds(5);
        while (squareRepository.getGameSquares(gameId).size() < 10 && LocalDateTime.now().isBefore(timeout)) {
            log.info("Found squares {}", squareRepository.getGameSquares(gameId).size());
        }

        List<Square> gameSquares = squareRepository.getGameSquares(gameId).stream()
                .map(s -> Square.builder().value(s.getValue()).build())
                .collect(Collectors.toList());
        squareRepository.removeGameSquares(gameId);
        return gameSquares;
    }

}
