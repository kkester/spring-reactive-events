package io.pivotal.producer.square;

public enum SquareValue {

    ONE_HUNDRED(100, 0),
    TWO_HUNDRED(200, 0),
    FIVE_HUNDRED(500, 0),
    ONE_THOUSAND(1000, 0),
    TWO_THOUSAND(2000, 0),
    FIVE_THOUSAND_PLUS_SPIN(5000, 1),
    TWO_SPINS(0, 2),
    WHAMMY(0, 0);

    private Integer value;
    private Integer spins;

    SquareValue(int value, int spins) {
        this.value = value;
        this.spins = spins;
    }

    public Integer score() {
        return this.value;
    }

    public Integer spins() {
        return this.spins;
    }
}
