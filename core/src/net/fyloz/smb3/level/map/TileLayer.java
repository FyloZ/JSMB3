package net.fyloz.smb3.level.map;

public enum TileLayer {
    DECORATION("Decoration"),
    FLOOR("Floor"),
    PLATEFORMS("Plateforms"),
    PIPES("Pipes"),
    BLOCKS("Blocks"),
    SHADOWS("Shadows"),
    POWERUPS("PowerUps");

    private String name;

    TileLayer(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
