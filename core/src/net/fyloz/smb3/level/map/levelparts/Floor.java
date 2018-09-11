package net.fyloz.smb3.level.map.levelparts;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.level.Level;
import net.fyloz.smb3.level.map.Tile;
import net.fyloz.smb3.level.map.TileLayer;
import net.fyloz.smb3.level.map.TilePropertyType;

import java.util.HashMap;
import java.util.Map;

public class Floor {

    private Level level;
    private Map<Vector2, Tile> tiles;

    public Floor(Level level) {
        this.level = level;

        tiles = new HashMap<>();

        TiledMap map = level.getMap();
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(TileLayer.FLOOR.toString());

        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);

                if (cell != null) {
                    Vector2 pos = new Vector2(x, y);
                    Tile tile = new Tile(tileLayer, pos);
                    //System.out.println(x + "/" + y + " -> " + tile.getProperties().getProperties().get(TilePropertyType.COLLISION));

                    createBody(tile);

                    tiles.put(pos, tile);
                }
            }
        }
    }

    private void createBody(Tile tile) {

        String collision = tile.getProperties().getProperties().get(TilePropertyType.COLLISION);
        String collide = tile.getProperties().getProperties().get(TilePropertyType.COLLIDE);

        if(collision != null && collision.equals("solid") && collide != null){
            Body body;
            BodyDef bd = new BodyDef();
            Fixture fixture;
            FixtureDef fd = new FixtureDef();
            EdgeShape shape = new EdgeShape();
            Vector2 pos = tile.getPosition();

            float[] vertices = addVertex(collide);

            shape.set(vertices[0], vertices[1], vertices[2], vertices[3]);

            bd.type = BodyDef.BodyType.StaticBody;
            bd.position.set(pos);

            fd.shape = shape;

            body = level.getWorld().createBody(bd);
            fixture = body.createFixture(fd);
            fixture.setUserData("Floor");
        }
    }

    private float[] addVertex(String collide) {
        String[] sides = collide.split(" ");
        float[] vertices = new float[4];

        for (String side : sides) {
            switch (side) {
                case "top":
                    vertices[0] = 0;
                    vertices[1] = 1;
                    vertices[2] = 1;
                    vertices[3] = 1;
                    break;
                case "bottom":
                    vertices[0] = 0;
                    vertices[1] = 0;
                    vertices[2] = 1;
                    vertices[3] = 0;
                    break;
                case "left":
                    vertices[0] = 0;
                    vertices[1] = 0;
                    vertices[2] = 0;
                    vertices[3] = 1;
                    break;
                case "right":
                    vertices[0] = 1;
                    vertices[1] = 0;
                    vertices[2] = 1;
                    vertices[3] = 1;
                    break;
            }
        }

        return vertices;
    }
}

