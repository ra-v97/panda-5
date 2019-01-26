package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;
import java.util.Objects;


public abstract class PowerUpDecorator implements PowerUpEffect {

    private float currentTimeout = 0;

    public boolean isExpired(){
        return currentTimeout < 0;
    }

    public void setTimeout(Float timeout){
        this.currentTimeout = timeout;
    }

    public void updateEffectTime(float dt){
        currentTimeout -= dt;
    }

    public abstract void loadManipulatedObject(Map<GameObjectType, Object> gameObjects);

    public abstract void clearPowerUpInfluence();

    @Override
    public abstract void applyEffect(float dt);

    @Override
    public void decorate(PowerUpDecorator decorator){}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PowerUpDecorator))
            return false;
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getClass());
    }
}
