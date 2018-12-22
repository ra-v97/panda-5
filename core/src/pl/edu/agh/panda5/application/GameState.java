package pl.edu.agh.panda5.application;

import pl.edu.agh.panda5.environment.EnvironmentalObject;
import pl.edu.agh.panda5.environment.GameObject;

import java.util.Set;

public class GameState {
    private Set<GameObject> gameObjects;
    private Set<EnvironmentalObject> environmentalObjects;
    private GameObject player;

    public void draw(){}
    public void update(){}
}
