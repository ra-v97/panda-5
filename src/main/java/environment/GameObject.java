package environment;

import collider.AbstractCollider;

public abstract class GameObject {
    private AbstractCollider collider;
    private String image;

    public void draw(){}
    public void update(){}
}
