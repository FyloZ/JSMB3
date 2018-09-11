package net.fyloz.smb3.level;

import com.badlogic.gdx.math.Vector3;

public enum LevelType {

    MAIN_SCREEN("Main screen", new Vector3(252, 216, 168)),
    OVERWORLD("Overworld", new Vector3(0, 232, 216));

    private String name;
    private Vector3 color;

    LevelType(String name, Vector3 color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Vector3 getColor() {
        return color;
    }
}
