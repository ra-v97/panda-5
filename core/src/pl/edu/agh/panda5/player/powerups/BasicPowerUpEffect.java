package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicPowerUpEffect implements PowerUpEffect {

    private List<PowerUpDecorator> activePowerUps = new ArrayList<>();
    private Map<GameObjectType, Object> mutableObjects;

    public BasicPowerUpEffect(Map<GameObjectType, Object> mo){
        mutableObjects = mo;
    }

    @Override
    public void applyEffect(float dt) {
        activePowerUps.forEach(pu -> pu.applyEffect(dt));
        removeFinishedEffects();
    }

    @Override
    public void decorate(PowerUpDecorator decorator) {
        decorator.loadManipulatedObject(mutableObjects);
        if(activePowerUps.contains(decorator)){
            int index = activePowerUps.indexOf(decorator);
            activePowerUps.get(index).setTimeout(Constants.DEFAULT_POWER_UP_TIMEOUT);
        }else{
            decorator.setTimeout(Constants.DEFAULT_POWER_UP_TIMEOUT);
            activePowerUps.add(decorator);
        }
    }

    private void removeFinishedEffects() {
        activePowerUps = activePowerUps.stream()
                .filter(pu-> {
                    if(!pu.isExpired()) return true;
                    else {
                        pu.clearPowerUpInfluence();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
