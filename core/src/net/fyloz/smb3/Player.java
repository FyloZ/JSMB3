package net.fyloz.smb3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.AnimationKeys.PlayerAnimations;
import net.fyloz.smb3.level.Level;

import java.util.HashMap;

public class Player implements ICollider {

    private Level level;

    private TextureAtlas atlas;
    private TextureRegion frame;
    private int keyframe = 1;
    private HashMap<AnimationKeys.PlayerAnimations, Integer> maxKeyframes;
    private AnimationKeys.PlayerAnimations state;

    {
        state = AnimationKeys.PlayerAnimations.WALKING1;
    }

    private Body body;

    private int animationFps = 10;
    private int direction = 1;
    // Build a dedicated timer
    private float timer = 0;

    private float width;
    private float height;
    private int x = 1;
    private int y = 2;

    public Player(Level level) {
        this.level = level;

        atlas = Resources.assets.get("tiles/mario_small.atlas");
        maxKeyframes = new HashMap<>();
        maxKeyframes.put(PlayerAnimations.WALKING1, 1); // Idle
        maxKeyframes.put(PlayerAnimations.WALKING, 2);

        frame = atlas.findRegion(state.toString().toLowerCase());

        width = (float) frame.getRegionWidth() / Resources.PPM;
        height = (float) frame.getRegionHeight() / Resources.PPM;

        createBody();
    }

    private void createBody() {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);

        body = level.getWorld().createBody(bd);
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(width / 2, height / 2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 70f;

        body.createFixture(fd);

        shape.dispose();
    }

    public void input() {
        if (Gdx.input.isKeyPressed(Keys.D)) {
            move(1, 0);
            state = PlayerAnimations.WALKING;
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            move(-1, 0);
            state = PlayerAnimations.WALKING;
        } else {
            state = PlayerAnimations.WALKING1;
        }
    }

    public void update() {
        input();

        // TIMER
        timer++;
        if (timer >= 60)
            timer = 0;

        if (timer % (60 / animationFps) == 0) {
            if (maxKeyframes.get(state) == 1)
                frame = atlas.findRegion(state.toString().toLowerCase());
            else
                frame = atlas.findRegion(state.toString().toLowerCase() + keyframe);

            if (keyframe >= maxKeyframes.get(state))
                keyframe = 1;
            else
                keyframe++;
        }

        width = frame.getRegionWidth() / Resources.PPM;
        height = frame.getRegionHeight() / Resources.PPM;

        // Camera
        OrthographicCamera camera = Resources.dynamicCamera;

        System.out.println(body.getPosition().x - Gdx.graphics.getWidth() / Resources.PPM / 2);

        if (body.getPosition().x - Gdx.graphics.getWidth() / Resources.PPM / 4 > 0) {
            camera.position.set(body.getPosition().x, camera.position.y, 0);
        }
    }

    public void render() {
        Resources.batch.setProjectionMatrix(Resources.dynamicCamera.combined);
        Resources.batch.draw(frame, body.getPosition().x / Resources.PPM, body.getPosition().y / Resources.PPM, width * direction, height);
    }

    public void move(int dx, int dy) {
        direction = dx < 0 ? -1 : 1;

        if (body.getPosition().x + dx < Gdx.graphics.getWidth() / Resources.PPM && body.getPosition().x + dx > 0 && body.getPosition().y + dy < Gdx.graphics.getHeight() / Resources.PPM && body.getPosition().y + dy > 0) {
            body.applyLinearImpulse(new Vector2(dx * Resources.PPM, dy * Resources.PPM), body.getWorldCenter(), true);
        }
    }

    public void jump(){
        body.applyLinearImpulse(new Vector2(0, 100), body.getWorldCenter(), true);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void onMove(int dx, int dy, TiledMapTileLayer.Cell cell) {
    }
}
