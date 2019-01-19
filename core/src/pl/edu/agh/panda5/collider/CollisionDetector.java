package pl.edu.agh.panda5.collider;

import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.player.powerups.PowerUp;
import pl.edu.agh.panda5.stages.GameStage;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameObjectType;


public class CollisionDetector implements ContactListener {
    private GameStage gameStage;

    public CollisionDetector(GameStage stage){
        this.gameStage = stage;
    }

    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        GameObjectType aType = ((GameObjectData) a.getUserData()).getType();
        GameObjectType bType = ((GameObjectData) b.getUserData()).getType();

        if (aType == GameObjectType.FEET_SENSOR || bType == GameObjectType.FEET_SENSOR) {
            if (aType == GameObjectType.PLATFORM || bType == GameObjectType.PLATFORM ||
                    aType == GameObjectType.OBSTACLE || bType == GameObjectType.OBSTACLE) {
                gameStage.getPlayer().landed();
            }
        }


        if (aType == GameObjectType.PLAYER) {
            if (((GameObjectData) b.getUserData()).isCoin()) {
                handlePlayerCoinContact(b);
            }
        } else if (bType == GameObjectType.PLAYER) {
            if (((GameObjectData) a.getUserData()).isCoin()) {
                handlePlayerCoinContact(a);
            }
        }

        if (aType == GameObjectType.POWER_UP && bType == GameObjectType.PLAYER) {
            handlePlayerPowerUpContact(a);
        } else if (bType == GameObjectType.POWER_UP && aType == GameObjectType.PLAYER) {
            handlePlayerPowerUpContact(b);
        }

        if (aType == GameObjectType.PLAYER && bType == GameObjectType.BULLET) {
            if(gameStage.getPlayer().canBeKilled()) gameStage.gameOver();
            ((GameObjectData) b.getUserData()).setFlaggedForDelete(true);
        }

        if (aType == GameObjectType.BULLET && bType == GameObjectType.PLAYER) {
            if(gameStage.getPlayer().canBeKilled()) gameStage.gameOver();
            ((GameObjectData) a.getUserData()).setFlaggedForDelete(true);
        }

        if (aType == GameObjectType.BULLET || bType == GameObjectType.BULLET) {
            if (aType == GameObjectType.PLAYER || bType == GameObjectType.PLAYER) {
                if(gameStage.getPlayer().canBeKilled()) gameStage.gameOver();
            }
        }

        if (aType == GameObjectType.BULLET && (bType == GameObjectType.PLATFORM || bType == GameObjectType.OBSTACLE)) {
            ((GameObjectData) a.getUserData()).setFlaggedForDelete(true);
        } else if (bType == GameObjectType.BULLET && (aType == GameObjectType.PLATFORM || aType == GameObjectType.OBSTACLE)) {
            ((GameObjectData) b.getUserData()).setFlaggedForDelete(true);
        }

    }

    private void handlePlayerCoinContact(Fixture coin) {
        GameObjectType coinType = ((GameObjectData) coin.getUserData()).getType();
        if (coinType == GameObjectType.COIN0) {
            gameStage.getPlayer().addPoints(Constants.COIN_VALUE[0]);
        } else if (coinType == GameObjectType.COIN1) {
            gameStage.getPlayer().addPoints(Constants.COIN_VALUE[1]);
        } else if (coinType == GameObjectType.COIN2) {
            gameStage.getPlayer().addPoints(Constants.COIN_VALUE[2]);
        }

        ((GameObjectData) coin.getUserData()).setFlaggedForDelete(true);
    }

    private void handlePlayerPowerUpContact(Fixture powerUp) {
        PowerUp pu = gameStage.getPowerUps()
                .stream()
                .filter((p) -> p.getBody().getFixtureList().contains(powerUp, true))
                .findFirst().orElseThrow(() -> new RuntimeException("Power up error"));
        gameStage.getPlayer().addEffect(pu.getEffect());
        ((GameObjectData) powerUp.getUserData()).setFlaggedForDelete(true);
    }


    public void preSolve(Contact contact, Manifold _oldManifold){
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        GameObjectType aType = ((GameObjectData) a.getUserData()).getType();
        GameObjectType bType = ((GameObjectData) b.getUserData()).getType();
        if ((aType == GameObjectType.PLAYER && bType == GameObjectType.PLATFORM) ||
                (aType == GameObjectType.PLATFORM && bType == GameObjectType.PLAYER) ) {
            if (gameStage.getPlayer().isDropping()){
                contact.setEnabled(false);
            }
        } else if ((aType == GameObjectType.FEET_SENSOR && bType == GameObjectType.OBSTACLE) ||
                (aType == GameObjectType.OBSTACLE && bType == GameObjectType.FEET_SENSOR) ) {
            if (gameStage.getPlayer().isDropping()){
                gameStage.getPlayer().stopDrop();
            }
        }
    }


    public void postSolve(Contact _contact, ContactImpulse _impulse){ }

    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (((GameObjectData) a.getUserData()).getType() == GameObjectType.FEET_SENSOR || ((GameObjectData) b.getUserData()).getType() == GameObjectType.FEET_SENSOR) {
            if ((((GameObjectData) a.getUserData()).getType() == GameObjectType.PLATFORM) || (((GameObjectData) b.getUserData()).getType() == GameObjectType.PLATFORM) ||
                    (((GameObjectData) a.getUserData()).getType() == GameObjectType.OBSTACLE) || (((GameObjectData) b.getUserData()).getType() == GameObjectType.OBSTACLE)) {
                gameStage.getPlayer().fall();
            }
        }
        if ((((GameObjectData) a.getUserData()).getType() == GameObjectType.PLAYER && ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLATFORM) ||
                (((GameObjectData) a.getUserData()).getType() == GameObjectType.PLATFORM && ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLAYER)) {
            gameStage.getPlayer().stopDrop();
        }
    }
}
