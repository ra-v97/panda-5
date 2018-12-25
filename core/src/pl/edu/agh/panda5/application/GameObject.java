package pl.edu.agh.panda5.application;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class GameObject extends Actor {
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

    @Override
    public void act(float delta) {
        super.act(delta);

        if(this.body.getPosition().x < 0){
            this.remove();
        }
    }
}
