package application;

import environment.EnvironmentalObject;
import environment.GameObject;

import java.util.Set;

public class GameState {
    private Set<GameObject> gameObjects;
    private Set<EnvironmentalObject> environmentalObjects;
    private GameObject player;

    public void draw(){}
    public void update(){}
}
