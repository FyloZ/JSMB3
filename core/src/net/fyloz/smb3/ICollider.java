package net.fyloz.smb3;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import net.fyloz.smb3.physic.RectangleBody;

public interface ICollider {
    void onMove(int dx, int dy, TiledMapTileLayer.Cell cell);

    Body getBody();
}
