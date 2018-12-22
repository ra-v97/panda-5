package pl.edu.agh.panda5.environment;

import pl.edu.agh.panda5.collider.AbstractCollider;

public abstract class GameObject {
    private AbstractCollider collider;
    private String image;

    public void draw(){}
    public void update(){}
}
