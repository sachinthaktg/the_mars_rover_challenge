package com.sr.roboticrover.model;

import java.util.Arrays;

public enum CardinalDirection {

    N("North"), E("East"), S("South"), W("West");

    private String direction;

    CardinalDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
