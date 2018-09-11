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

public class Blocks {

    private Level level;
    private Map<Vector2, Tile> tiles;

    public Blocks(Level level) {
        this.level = level;

        tiles = new HashMap<>();

        TiledMap map = level.getMap();
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(TileLayer.BLOCKS.toString());

        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);

                if (cell != null) {
                    Vector2 pos = new Vector2(x, y);
                    Tile tile = new Tile(tileLayer, pos);

                    createBody(tile);

                    tiles.put(pos, tile);
                }
            }
        }
    }

    private void createBody(Tile tile) {

        String collision = tile.getProperties().getProperties().get(TilePropertyType.COLLISION);

        if (collision != null && collision.equals("solid")) {
            Body body;
            BodyDef bd = new BodyDef();
            Fixture fixture;
            FixtureDef fd = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            Vector2 pos = tile.getPosition();

            tile.getProperties().addCustomProperty("ground", new float[]{
                    pos.x, pos.y + 1, pos.x + 1, pos.y + 1
            });

            shape.setAsBox(0.5f, 0.5f);

            bd.type = BodyDef.BodyType.StaticBody;
            bd.position.set(pos.x + 0.5f, pos.y + 0.5f);

            fd.shape = shape;

            body = level.getWorld().createBody(bd);
            fixture = body.createFixture(fd);
            fixture.setUserData(tile.getProperties());
        }
    }
}
