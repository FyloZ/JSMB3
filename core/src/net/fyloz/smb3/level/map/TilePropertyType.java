package net.fyloz.smb3.level.map;

public enum TilePropertyType {

    ACTIONABLE("actionable"),
    COLLIDE("collide"),
    COLLISION("collision"),
    COLLECTABLE("collectable"),
    TYPE("type"),
    SOLIDITY("solidity");

    private String type;

    TilePropertyType(String type) {
        this.type = type;
    }
}
