package net.fyloz.smb3.level.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileProperties {

    private TileLayer layer;
    private Map<TilePropertyType, String> properties;

    private boolean isAir = false;

    public TileProperties(TiledMapTileLayer.Cell cell, TileLayer layer) {
        this.layer = layer;

        /**if(cell == null){
            isAir = true;
            return;
        }**/

        properties = new HashMap<>();

        TiledMapTile tile = cell.getTile();
        MapProperties tileProperties = tile.getProperties();

        Iterator<String> iterator = tileProperties.getKeys();
        while(iterator.hasNext()){
            String property = iterator.next();
            String value = tileProperties.get(property).toString();

            properties.put(TilePropertyType.valueOf(property.toUpperCase()), value);
        }
    }

    public TileLayer getLayer() {
        return layer;
    }

    public Map<TilePropertyType, String> getProperties() {
        return properties;
    }

    public boolean isAir() {
        return isAir;
    }
}
