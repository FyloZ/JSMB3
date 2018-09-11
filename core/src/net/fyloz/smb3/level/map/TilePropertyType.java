package net.fyloz.smb3.level.map;

public enum TilePropertyType {

    COLLISION("collision"),
    COLLIDE("collide"),
    ACTIONABLE("actionable"),
    COLLECTABLE("collectable"),
    TYPE("type");

    private String type;

    TilePropertyType(String type) {
        this.type = type;
    }
}
