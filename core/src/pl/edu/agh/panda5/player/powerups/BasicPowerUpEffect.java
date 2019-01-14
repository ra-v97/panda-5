package pl.edu.agh.panda5.player.powerups;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BasicPowerUpEffect implements PowerUpEffect {

    private List<PowerUpDecorator> activePowerUps = new LinkedList<>();

    @Override
    public void applyEffect() {
        activePowerUps.forEach(PowerUpDecorator::applyEffect);
        removeFinishedEffects();
    }

    @Override
    public void decorate(PowerUpDecorator decorator) {
        activePowerUps.add(decorator);
    }

    private void removeFinishedEffects() {
        for(int i = 0 ; i < activePowerUps.size(); i++){
            if (activePowerUps.get(i).isExpired()) {
                activePowerUps.remove(i);
            }
        }
    }
}
