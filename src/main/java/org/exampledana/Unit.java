package org.exampledana;

public enum Unit {
    mm(1),
    cm(10),
    dm(100),
    m(1000),
    km(1000000);
    int value;

    Unit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
