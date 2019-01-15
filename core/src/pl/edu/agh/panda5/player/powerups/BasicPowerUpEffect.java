package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.utils.Constants;

import java.util.LinkedList;
import java.util.List;

public class BasicPowerUpEffect implements PowerUpEffect {

    private List<PowerUpDecorator> activePowerUps = new LinkedList<>();

    @Override
    public void applyEffect(float dt) {
        activePowerUps.forEach(pu -> pu.applyEffect(dt));
        removeFinishedEffects();
    }

    @Override
    public void decorate(PowerUpDecorator decorator) {
        decorator.setTimeout(Constants.DEFAULT_POWER_UP_TIMEOUT);
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
