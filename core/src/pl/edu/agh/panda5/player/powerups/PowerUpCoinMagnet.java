package pl.edu.agh.panda5.player.powerups;

import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;
import java.util.Set;

public class PowerUpCoinMagnet extends PowerUpDecorator {

    private Set<Coin> activeCoins;
    private Player player;

    @Override
    public void applyEffect(float dt){
        for(Coin activeCoin : activeCoins){
            if(activeCoin.isAvailable()){
                if(activeCoin.getBody().getPosition().dst(player.getBody().getPosition()) < Constants.MAGNET_EFFECT_RANGE){
                    player.addPoints(activeCoin.getValue());
                    ((GameObjectData) activeCoin.getBody().getFixtureList().get(0).getUserData()).setFlaggedForDelete(true);
                }
            }
        }
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
