package net.fyloz.smb3.level.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Tile {

    private TileProperties properties;
    private Vector2 pos;

    public Tile(TiledMapTileLayer layer, Vector2 pos) {
        this.pos = pos;

        TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x, (int) pos.y);
        properties = new TileProperties(cell, TileLayer.valueOf(layer.getName().toUpperCase()));
    }

    public Vector2 getPosition() {
        return pos;
    }

    public TileProperties getProperties() {
        return properties;
    }
}
