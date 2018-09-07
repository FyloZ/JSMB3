package net.fyloz.smb3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import net.fyloz.smb3.level.Level;

public class Main extends Game {

    public final static AssetManager assets = new AssetManager();

    private Level level;
    private Player player;

    public void init() {
        Resources.assets = new AssetManager();

        Resources.batch = new SpriteBatch();

        Resources.dynamicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Resources.dynamicCamera.position.set(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 4f, 0);
        Resources.dynamicCamera.zoom = 0.5f / Resources.PPM;
        Resources.dynamicCamera.update();
        Resources.staticCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Resources.staticCamera.position.set(Resources.staticCamera.viewportWidth / 2f, Resources.staticCamera.viewportHeight / 2f, 0);
        Resources.staticCamera.update();
    }

    public void load() {
        Resources.assets.load("tiles/mario_small.atlas", TextureAtlas.class);
        Resources.assets.load("musics/overworld.mp3", Music.class);
        Resources.assets.load("tiles/stage.png", Texture.class);

        while (Resources.assets.getProgress() < 1)
            Resources.assets.update();
    }

    @Override
    public void create() {
        init();
        load();

        level = new Level("1/1");
        player = new Player(level);
    }

    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.I))
            Resources.dynamicCamera.zoom += 0.05f;
        if(Gdx.input.isKeyPressed(Input.Keys.O))
            Resources.dynamicCamera.zoom -= 0.05f;

        Resources.dynamicCamera.update();
        Resources.staticCamera.update();

        player.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 232, 216, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        level.render();
        Resources.batch.begin();
        player.render();
        Resources.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Resources.dynamicCamera.setToOrtho(false, width, height);
        Resources.staticCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        level.dispose();

        Resources.assets.dispose();
        Resources.batch.dispose();
    }
}
