package pl.edu.agh.panda5.player.powerups;

public abstract class PowerUpDecorator implements PowerUpEffect {

    private boolean toRemove = false;

    public boolean isExpired(){
        return toRemove;
    }

    @Override
    public abstract void applyEffect();

    @Override
    public void decorate(PowerUpDecorator decorator){}
}
