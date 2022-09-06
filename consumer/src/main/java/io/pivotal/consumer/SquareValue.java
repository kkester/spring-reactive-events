package io.pivotal.consumer;

public enum SquareValue {

    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(3000),
    WHAMMY(0);

    private Integer value;

    SquareValue(int value) {
        this.value = value;
    }

    public Integer score() {
        return this.value;
    }
}
