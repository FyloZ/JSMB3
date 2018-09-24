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

public class Plateforms implements LevelPart {

    private Level level;
    private Map<Vector2, Tile> tiles;

    public Plateforms(Level level) {
        this.level = level;

        tiles = new HashMap<>();

        TiledMap map = level.getMap();
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(TileLayer.PLATEFORMS.toString());

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

    @Override
    public void createBody(Tile tile) {
        String collision = tile.getProperties().getProperties().get(TilePropertyType.COLLISION);

        if (collision != null && collision.equals("sided")) {
            Body body;
            BodyDef bd = new BodyDef();
            Fixture fixture;
            FixtureDef fd = new FixtureDef();
            EdgeShape shape = new EdgeShape();
            Vector2 pos = tile.getPosition();

            tile.getProperties().addCustomProperty("plateformY", pos.y + 1);
            tile.getProperties().addCustomProperty("ground", "ALL");

            shape.set(0, 1, 1, 1);

            bd.type = BodyDef.BodyType.StaticBody;
            bd.position.set(pos);

            fd.shape = shape;

            body = level.getWorld().createBody(bd);
            fixture = body.createFixture(fd);
            fixture.setUserData(tile.getProperties());
        }
    }

    @Override
    public Tile getTile(int x, int y) {
        return tiles.get(new Vector2(x, y));
    }
}
