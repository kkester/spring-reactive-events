package io.pivotal.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Square {
    private UUID gameId;
    private SquareValue value;
    private Boolean selected;
}
