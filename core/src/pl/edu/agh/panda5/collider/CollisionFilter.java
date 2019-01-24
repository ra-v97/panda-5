package pl.edu.agh.panda5.collider;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameObjectType;

public class CollisionFilter implements ContactFilter {
        public boolean shouldCollide (Fixture a, Fixture b) {
            if (((GameObjectData) a.getUserData()).getType() == GameObjectType.PLATFORM &&
                    ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLAYER) {
                Vector2 position = b.getBody().getPosition();
                return (position.y - Constants.RUNNER_HEIGHT/2 > a.getBody().getPosition().y);
            } else if (((GameObjectData) a.getUserData()).getType() == GameObjectType.PLAYER &&
                    ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLATFORM) {
                Vector2 position = a.getBody().getPosition();
                return (position.y - Constants.RUNNER_HEIGHT/2 > b.getBody().getPosition().y);
            } else{
                return true;
            }
        }
}
