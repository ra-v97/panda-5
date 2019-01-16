package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;
import java.util.Set;

public class PowerUpCoinMagnet extends PowerUpDecorator {

    private Set<Coin> activeCoins;
    private Player player;

    @Override
    public void applyEffect(float dt){
        updateEffectTime(dt);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadManipulatedObject(Map<GameObjectType, Object> ob){
        activeCoins = (Set<Coin>) ob.get(GameObjectType.COINS);
        player = (Player)ob.get(GameObjectType.PLAYER);
    }

    @Override
    public void clearPowerUpInfluence(){

    }
}
