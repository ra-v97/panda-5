package pl.edu.agh.panda5.player.powerups;

public abstract class PowerUpDecorator implements PowerUpEffect {

    private float currentTimeout = 0;

    public boolean isExpired(){
        return currentTimeout < 0;
    }

    public void setTimeout(Float timeout){
        this.currentTimeout = timeout;
    }

    public void updateEffectTime(float dt){
        currentTimeout -= dt;
    }

    @Override
    public abstract void applyEffect(float dt);

    @Override
    public void decorate(PowerUpDecorator decorator){}
}
