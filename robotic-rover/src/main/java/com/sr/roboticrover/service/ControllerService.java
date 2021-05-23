package com.sr.roboticrover.service;

import com.sr.roboticrover.model.CardinalDirection;
import com.sr.roboticrover.model.ControllerParam;
import com.sr.roboticrover.model.Move;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControllerService extends BaseService {

    public void defineGridSize(byte x, byte y) {
        ControllerParam.girdSize[0] = x;
        ControllerParam.girdSize[1] = y;
    }

    public ResponseEntity<String> definePosition(byte x, byte y, char direction) {
        ResponseEntity responseEntity = validatePositionValues(x, y, direction);
        if (responseEntity.getStatusCodeValue() == 200) {
            ControllerParam.latestPosition[0] = x;
            ControllerParam.latestPosition[1] = y;
            ControllerParam.latestDirection = CardinalDirection.valueOf(String.valueOf(direction));
            return ResponseEntity.ok("Robotic Rover ".concat(ControllerParam.serial).concat(" has moved to the given position"));
        }
        return responseEntity;
    }

    /**
     * validate the robotic rover position and send back the response
     *
     * @param x
     * @param y
     * @param direction
     * @return ResponseEntity with validation messages
     */
    private ResponseEntity<String> validatePositionValues(byte x, byte y, char direction) {
        if (x < 0 || x > ControllerParam.girdSize[0]) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid x value.(x values should be between 0 and ".concat(String.valueOf(ControllerParam.girdSize[0])).concat(")"));
        } else if (y < 0 || y > ControllerParam.girdSize[0]) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid x value.(x values should be between 0 and ".concat(String.valueOf(ControllerParam.girdSize[0])).concat(")"));
        } else {
            try {
                CardinalDirection.valueOf(String.valueOf(direction));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid direction value.(possible values are ".concat(String.join("/ ", getNames(CardinalDirection.class))).concat(")"));
            }
        }
        return ResponseEntity.ok("");
    }

    public ResponseEntity<String> move(String direction) {
        // we are defining all movement creators in capital letters so to reduce the user errors i have make sure that the direction string is in UPPERCASE
        // and then get directions as char array and validate with movement chars
        List<Character> characters = convertToChar(direction.toUpperCase());
        try {
            List<Move> moves = validateDirections(characters);
            // move robotic according to the command list
            moves.forEach(this::executeCommand);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid move command.(possible values are ".concat(String.join("/ ", getNames(Move.class))).concat(")"));
        }
        return ResponseEntity.ok(String.valueOf(ControllerParam.latestPosition[0]).concat(String.valueOf(ControllerParam.latestPosition[1])).concat(ControllerParam.latestDirection.toString()));
    }

    //    Y
    //     __ __ __ __ __                    N
    //    |__|__|__|__|__|                   |
    //    |__|__|__|__|__|            E _____|______ W
    //    |__|__|__|__|__|                   |
    //    |__|__|__|__|__|                   |
    //    |__|__|__|__|__| X                 S

    /**
     * ‘M’ move forward one grid point, and maintains the same heading
     * ‘L’ and ‘R’ makes the rover spin 90 degrees left or right respectively, without moving from its current spot.
     * @param move list of Move commands
     */
    private void executeCommand(Move move) {
        // if command to move
        if (move.equals(Move.M)) {
            // robotic current direction is North then move robotic into the next grid through Y axis
            if (ControllerParam.latestDirection.equals(CardinalDirection.N)) {
                ControllerParam.latestPosition[1] += 1;
            // robotic current direction is West then move robotic into the next grid through X axis
            } else if (ControllerParam.latestDirection.equals(CardinalDirection.W)) {
                ControllerParam.latestPosition[0] += 1;
            // robotic current direction is South then move robotic into the next grid through down the Y axis
            } else if (ControllerParam.latestDirection.equals(CardinalDirection.S)) {
                ControllerParam.latestPosition[1] -= 1;
            // robotic current direction is East then move robotic into the left grid through the X axis
            } else if (ControllerParam.latestDirection.equals(CardinalDirection.E)) {
                ControllerParam.latestPosition[0] -= 1;
            }
        } else if (move.equals(Move.R)) {
            turn(1);
        } else if (move.equals(Move.L)) {
            turn(-1);
        }
    }

    /**
     * ‘L’ and ‘R’ makes the rover spin 90 degrees left or right respectively
     * @param i
     */
    private void turn(int i) {
        // get current direction
        Integer integer = ControllerParam.cardinalDirectionVal.get(ControllerParam.latestDirection);
        // get the new direction
        int x = ((integer == 0 && i < 0 ? 360 : integer) + (90 * i));
        ControllerParam.latestDirection = getKey(ControllerParam.cardinalDirectionVal, (x == 360 ? 0 : x));
    }

    /**
     * validate move commands
     *
     * @param characters move commands as char array
     * @return ResponseEntity with validation messages
     */
    private List<Move> validateDirections(List<Character> characters) throws IllegalArgumentException {
        return characters.stream().map(c -> Move.valueOf(c.toString())).collect(Collectors.toList());
    }
}



















