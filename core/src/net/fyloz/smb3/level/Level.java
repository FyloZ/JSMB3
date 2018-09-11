package net.fyloz.smb3.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import net.fyloz.smb3.Resources;
import net.fyloz.smb3.level.map.levelparts.Floor;
import net.fyloz.smb3.physic.CollisionManager;

public class Level {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Box2DDebugRenderer debug;
    private boolean debugging = false;

    private Music music;

    private World world;

    public Level(String filename) {
        LevelLoader loader = new LevelLoader(filename);
        map = loader.getMap();

        renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        music = Resources.assets.get("musics/overworld.mp3");
        music.play();

        world = new World(new Vector2(0, -30f), true);
        world.setContactListener(new CollisionManager());
        debug = new Box2DDebugRenderer();

        new Floor(this);
    }

    public void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            debugging = !debugging;
    }

    public void render() {
        update();

        renderer.setView(Resources.dynamicCamera);
        renderer.render();

        if (debugging)
            debug.render(world, Resources.dynamicCamera.combined);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
        music.pause();
    }

}
