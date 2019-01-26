package pl.edu.agh.panda5.utils;

public class GameObjectData {

    GameObjectType type;

    public GameObjectData(GameObjectType type) {
        this.type = type;
    }

    private boolean isFlaggedForDelete = false;

    public boolean isCoin() {
        return (type == GameObjectType.COIN0 || type == GameObjectType.COIN1 || type == GameObjectType.COIN2);
    }

    public GameObjectType getType() {
        return type;
    }

    public boolean isFlaggedForDelete() {
        return isFlaggedForDelete;
    }

    public void setFlaggedForDelete(boolean flag) {
        isFlaggedForDelete = flag;
    }
}
