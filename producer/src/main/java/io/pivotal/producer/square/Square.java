package io.pivotal.producer.square;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Square {
    private SquareValue value;
    private Boolean selected;
}
