package io.pivotal.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Game {
    private UUID gameId;
    private Integer lives;
    private Integer score;
    private Integer totalScore;
    @Builder.Default
    private List<Square> squares = new ArrayList<>();
}
