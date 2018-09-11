package net.fyloz.smb3.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import net.fyloz.smb3.Player;
import net.fyloz.smb3.Resources;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object uda = contact.getFixtureA().getUserData();
        Object udb = contact.getFixtureB().getUserData();

        if (uda instanceof Player) {
            Player player = (Player) uda;
            if (player.isJumping)
                player.isJumping = false;
        } else if (udb instanceof Player) {
            Player player = (Player) udb;
            if (player.isJumping)
                player.isJumping = false;
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
