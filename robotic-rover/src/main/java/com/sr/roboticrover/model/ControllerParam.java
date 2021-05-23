package com.sr.roboticrover.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ControllerParam {

    public static String serial = "RR64645465SD4FSDF5464SDF";
    /**
     * this variable is used to store plateau divided grid size
     * and i'm assuming it'll be a whole number between 1 to 127
     */
    public static byte[] girdSize = new byte[]{5,5};
    /**
     * this variable is used to store the latest position of the robotic
     */
    public static byte[] latestPosition = new byte[]{0,0};
    /**
     * this variable is used to store the latest direction of the robotic rover
     */
    public static CardinalDirection latestDirection = CardinalDirection.N;

    public static Map<CardinalDirection, Integer> cardinalDirectionVal = new HashMap<>();

    static {
        cardinalDirectionVal.put(CardinalDirection.N, 0);
        cardinalDirectionVal.put(CardinalDirection.W, 90);
        cardinalDirectionVal.put(CardinalDirection.S, 180);
        cardinalDirectionVal.put(CardinalDirection.E, 270);
    }
}
