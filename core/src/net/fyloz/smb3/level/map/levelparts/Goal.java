package net.fyloz.smb3.level.map.levelparts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.Resources;
import net.fyloz.smb3.level.Level;

public class Goal {

    private Level level;
    private MapObject goalObject;
    private Vector2 pos;

    private TextureAtlas atlas;
    private TextureRegion frame;
    private int keyframe = 1;
    private int animationFps = 3;
    private int timer;

    public Goal(Level level) {
        this.level = level;

        atlas = Resources.assets.get("textures/goal.atlas");
        frame = atlas.findRegion("goal1");

        goalObject = level.getMap().getLayers().get("Goal").getObjects().get(0);
        pos = new Vector2(Float.parseFloat(goalObject.getProperties().get("x").toString()) / Resources.PPM - 2.5f, Float.parseFloat(goalObject.getProperties().get("y").toString()) / Resources.PPM + 0.5f);
        createBody();
    }

    private void createBody() {
        Body body;
        BodyDef bd = new BodyDef();
        Fixture fixture;
        FixtureDef fd = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(1f, 1f);

        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(pos.x + 0.5f, pos.y + 0.5f);

        fd.shape = shape;

        body = level.getWorld().createBody(bd);
        fixture = body.createFixture(fd);
        fixture.setUserData("Goal");
    }

    private void update() {
        timer++;
        if (timer >= 60)
            timer = 0;

        if (timer % (60 / animationFps) == 0) {
            frame = atlas.findRegion("goal" + keyframe);

            if (keyframe >= 3)
                keyframe = 1;
            else
                keyframe++;
        }
    }

    public void render() {
        update();

        Resources.batch.begin();
        Resources.batch.setProjectionMatrix(Resources.dynamicCamera.combined);
        Resources.batch.draw(frame, pos.x, pos.y, frame.getRegionWidth() / Resources.PPM, frame.getRegionHeight() / Resources.PPM);
        Resources.batch.end();
    }
}
