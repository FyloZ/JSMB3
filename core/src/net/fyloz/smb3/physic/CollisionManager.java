package net.fyloz.smb3.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import net.fyloz.smb3.Player;
import net.fyloz.smb3.Resources;
import net.fyloz.smb3.level.map.TileProperties;
import net.fyloz.smb3.level.map.levelparts.Floor;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object uda = contact.getFixtureA().getUserData();
        Object udb = contact.getFixtureB().getUserData();

        if (udb instanceof Player && uda instanceof TileProperties) {
            Object tmp = uda;
            uda = udb;
            udb = tmp;
        }

        if (uda instanceof Player && udb instanceof TileProperties) {
            Player player = (Player) uda;

            if (player.isJumping) {
                Object isGround = ((TileProperties) udb).getCustomProperty("ground");


                if (isGround instanceof float[]) {
                    float[] groundSurface = (float[]) isGround;

                    if(player.getBody().getPosition().x + player.getWidth() / 2 >= groundSurface[0] && player.getBody().getPosition().x - player.getWidth() / 2 <= groundSurface[2] && player.getBody().getPosition().y - player.getHeight() / 2 >= groundSurface[1] && player.getBody()
                            .getPosition().y - player.getHeight() / 2 <= groundSurface[3] + 0.5f)
                        player.isJumping = false;
                } else if (isGround instanceof String && isGround.equals("ALL")) {
                    player.isJumping = false;
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object uda = contact.getFixtureA().getUserData();
        Object udb = contact.getFixtureB().getUserData();
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
}
