package player.powerups;

import java.util.List;

public abstract class PowerUpDecorator implements PowerUpEffect {
    protected List<PowerUpEffect> powerUps;
    @Override
    public void applyEffect(){}
}
