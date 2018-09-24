package net.fyloz.smb3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.fyloz.smb3.AnimationKeys.PlayerAnimations;
import net.fyloz.smb3.level.Level;

import java.util.HashMap;

public class Player {

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
    private int x = 130;
    private int y = 2;

    public boolean isJumping = false;
    private float maxSpeed = 6f;

    public Player(Level level) {
        this.level = level;

        atlas = Resources.assets.get("textures/mario_small.atlas");
        maxKeyframes = new HashMap<>();
        maxKeyframes.put(PlayerAnimations.WALKING1, 1); // Idle
        maxKeyframes.put(PlayerAnimations.WALKING, 2);
        maxKeyframes.put(PlayerAnimations.JUMPING1, 1);

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
        body.setFixedRotation(true);
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(width / 2, height / 2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 70f;
        fd.friction = 0.15f;

        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);

        shape.dispose();
    }

    public void input() {
        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            move(1, 0);
            state = PlayerAnimations.WALKING;
        } else if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            move(-1, 0);
            state = PlayerAnimations.WALKING;
        } else {
            state = PlayerAnimations.WALKING1;
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            jump();
        }
    }

    public void update() {
        input();

        if (isJumping)
            state = PlayerAnimations.JUMPING1;

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

        // Camera
        OrthographicCamera camera = Resources.dynamicCamera;
        if (body.getPosition().x - width - camera.viewportWidth / Resources.PPM / 6 > 0 && body.getPosition().x - width - camera.viewportWidth / Resources.PPM / 6 < 160.5f) {
            camera.position.set(body.getPosition().x, camera.position.y, 0);
        }
    }

    public void render() {
        Resources.batch.setProjectionMatrix(Resources.dynamicCamera.combined);
        Resources.batch.draw(frame, body.getPosition().x - width / 2 + (direction < 0 ? 0.75f : 0), body.getPosition().y - height / 2 - (4 / Resources.PPM), (float) frame.getRegionWidth() / Resources.PPM * direction, (float) frame.getRegionHeight() / Resources.PPM);
    }

    private void move(int dx, int dy) {
        direction = dx < 0 ? -1 : 1;

        float cameraPosX = Resources.dynamicCamera.position.x;
        if (body.getPosition().x < 175 && body.getPosition().x + dx > 0 && (body.getLinearVelocity().x <= maxSpeed
                && body.getLinearVelocity().x >= -maxSpeed)) {
            body.applyLinearImpulse(new Vector2(dx * Resources.PPM, dy * Resources.PPM), body.getWorldCenter(), true);
        }
    }

    private void jump() {
        if (!isJumping) {
            body.applyLinearImpulse(new Vector2(0, 850), body.getWorldCenter(), true);
            isJumping = true;
        }
    }

    public Body getBody() {
        return body;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
