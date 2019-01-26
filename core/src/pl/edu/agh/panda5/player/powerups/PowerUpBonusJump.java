package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;


public class PowerUpBonusJump extends PowerUpDecorator {

    private Player player;
    private boolean isRunning = false;

    @Override
    public void applyEffect(float dt){
        if(!isRunning){
            isRunning =true;
            player.doubleJumpOn();
        }
        updateEffectTime(dt);
    }

    @Override
    public void loadManipulatedObject(Map<GameObjectType, Object> ob){
        player = (Player)ob.get(GameObjectType.PLAYER);
    }

    @Override
    public void clearPowerUpInfluence(){
        player.doubleJumpOff();
    }
}
