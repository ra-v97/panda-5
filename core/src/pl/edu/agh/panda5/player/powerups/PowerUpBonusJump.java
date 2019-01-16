package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;


public class PowerUpBonusJump extends PowerUpDecorator {

    private Player player;

    @Override
    public void applyEffect(float dt){
        updateEffectTime(dt);
    }

    @Override
    public void loadManipulatedObject(Map<GameObjectType, Object> ob){
        player = (Player)ob.get(GameObjectType.PLAYER);
    }

    @Override
    public void clearPowerUpInfluence(){

    }
}
