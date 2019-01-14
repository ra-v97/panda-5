package pl.edu.agh.panda5.player.powerups;

import java.util.LinkedList;
import java.util.List;

public class BasicPowerUpEffect implements PowerUpEffect {

    private List<PowerUpDecorator> activePowerUps = new LinkedList<>();

    @Override
    public void applyEffect(){
    }
}
