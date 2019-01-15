package pl.edu.agh.panda5.player.powerups;

public interface PowerUpEffect {
    void applyEffect(float dt);
    void decorate(PowerUpDecorator decorator);
}
