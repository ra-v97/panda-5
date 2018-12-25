package pl.edu.agh.panda5.collider;

import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.stages.GameStage;


public class CollisionDetector implements ContactListener {
    private GameStage stage;

    public CollisionDetector(GameStage stage){
        this.stage = stage;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        stage.beginContact(a, b);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        stage.endContact(a, b);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
