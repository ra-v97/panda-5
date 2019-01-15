package pl.edu.agh.panda5.player.powerups;

public class PowerUpBonusJump extends PowerUpDecorator {
    @Override
    public void applyEffect(float dt){
        updateEffectTime(dt);
    }
}
