package pl.edu.agh.panda5.application;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.edu.agh.panda5.collider.AbstractCollider;

public abstract class GameObject extends Actor {
    private AbstractCollider collider;
    private String image;
    protected Body body;

    public GameObject(Body body) {
        this.body = body;
    }

    public void draw(){}
    public void update(){}
    public Body getBody(){
        return body;
    }
}
