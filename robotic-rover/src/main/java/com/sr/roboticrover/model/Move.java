package com.sr.roboticrover.model;

public enum Move {

    L("Left"), R("Right"), M("Move Forward");

    private String command;

    Move(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
