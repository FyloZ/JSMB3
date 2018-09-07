package net.fyloz.smb3.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;

public class LevelLoader {

    private TiledMap map;

    // Map layers
    private HashMap<String, TiledMapTileLayer> layers;

    public LevelLoader(String levelName) {
        map = new TmxMapLoader().load("levels/" + levelName + ".tmx");

        layers = new HashMap<>();
        layers.put("PowerUps", (TiledMapTileLayer) map.getLayers().get("PowerUps"));
        layers.put("Shadows", (TiledMapTileLayer) map.getLayers().get("Shadows"));
        layers.put("Blocks", (TiledMapTileLayer) map.getLayers().get("Blocks"));
        layers.put("Pipes", (TiledMapTileLayer) map.getLayers().get("Pipes"));
        layers.put("Plateforms", (TiledMapTileLayer) map.getLayers().get("Plateforms"));
        layers.put("Floor", (TiledMapTileLayer) map.getLayers().get("Floor"));
        layers.put("Decoration", (TiledMapTileLayer) map.getLayers().get("Decoration"));
    }

    public TiledMapTileLayer getLayer(String layer) {
        return layers.get(layer);
    }

    public HashMap<String, TiledMapTileLayer> getLayers() {
        return layers;
    }

    public TiledMap getMap() {
        return map;
    }
}
