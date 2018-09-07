package net.fyloz.smb3.level.levelparts;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.Resources;
import net.fyloz.smb3.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {

    private Level level;
    private Map<TiledMapTileLayer.Cell, MapProperties> cells;

    public Floor(Level level) {
        this.level = level;

        cells = new HashMap<>();

        TiledMapTileLayer layer = (TiledMapTileLayer) this.level.getMap().getLayers().get("Floor");
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    createBody(cell, x, y);

                    cells.put(cell, cell.getTile().getProperties());
                }
            }
        }
    }

    private void createBody(TiledMapTileLayer.Cell cell, int x, int y) {
        Body body;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(x + 0.5f, y + 0.5f);

        body = level.getWorld().createBody(bd);
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(0.5f,0.5f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;

        Fixture fixture = body.createFixture(fd);
        // For access tile's properties with a Contact
        fixture.setUserData(cell.getTile().getProperties());

        shape.dispose();
    }

    public Map getCells() {
        return cells;
    }
}
