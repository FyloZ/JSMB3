package net.fyloz.smb3.level.map.levelparts;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.Resources;
import net.fyloz.smb3.level.Level;
import net.fyloz.smb3.level.map.Tile;
import net.fyloz.smb3.level.map.TileLayer;
import net.fyloz.smb3.level.map.TileProperties;

import java.util.HashMap;
import java.util.Map;

public class Pipes {

    private Level level;
    private Map<Vector2, RectangleMapObject> pipes;
    private Orientation orientation;

    public Pipes(Level level) {
        this.level = level;

        pipes = new HashMap<>();

        TiledMap map = level.getMap();
        MapLayer layer = map.getLayers().get("PipeObjects");

        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject && object.getProperties().get("orientation") != null) {
                RectangleMapObject rectangle = (RectangleMapObject) object;
                orientation = Orientation.valueOf(rectangle.getProperties().get("orientation").toString().toUpperCase());

                createBody(rectangle);

                Vector2 pos = new Vector2(rectangle.getRectangle().getX(), rectangle.getRectangle().getY());
                pipes.put(pos, rectangle);
            }
        }
    }

    public void createBody(RectangleMapObject object) {
        Body body;
        BodyDef bd = new BodyDef();
        Fixture fixture;
        FixtureDef fd = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2 pos = new Vector2(object.getRectangle().getX() / Resources.PPM + 1f, object.getRectangle().getY() / Resources.PPM + 1.5f);

        TileProperties properties = new TileProperties(null, TileLayer.PIPES);
        properties.addCustomProperty("ground", "ALL");
        properties.addCustomProperty("pipeOrientation", orientation);

        shape.setAsBox((object.getRectangle().getWidth() + 1) / 2 / Resources.PPM, object.getRectangle().getHeight() / 2 / Resources.PPM);

        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(pos);

        fd.shape = shape;

        body = level.getWorld().createBody(bd);
        body.setGravityScale(0f);
        fixture = body.createFixture(fd);
        fixture.setUserData(properties);
    }

    public Tile getTile(int x, int y) {
        return null;
    }

    public enum Orientation {
        LEFT("left"),
        RIGHT("right"),
        TOP("top"),
        BOTTOM("bottom");

        private String name;

        Orientation(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Type {
        IN, OUT, ALL, NULL
    }
}
