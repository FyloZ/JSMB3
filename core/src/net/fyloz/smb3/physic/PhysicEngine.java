package net.fyloz.smb3.physic;

import com.badlogic.gdx.math.Vector2;
import net.fyloz.smb3.ICollider;

import java.util.ArrayList;

public class PhysicEngine {

    private ArrayList<ICollider> physicObjects;

    private float gravity = 9.8f;

    public PhysicEngine() {
        physicObjects = new ArrayList<>();
    }

    public void registerCollider(ICollider collider) {
        physicObjects.add(collider);
    }

    public void update() {
       /**physicObjects.forEach((c) -> {
            if (c.getBody().isGravityApplied())
                c.getBody().applyForce(new Vector2(0, -9.8f));


        });**/
    }

    public void clear() {
        physicObjects.clear();
    }

}
