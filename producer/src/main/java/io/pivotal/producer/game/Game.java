package io.pivotal.producer.game;

import io.pivotal.producer.square.Square;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.Link;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Game {
    private URI play;
    private Integer spins;
    private Integer score;
    private Integer totalScore;
    @Builder.Default
    private List<Square> squares = new ArrayList<>();
}
