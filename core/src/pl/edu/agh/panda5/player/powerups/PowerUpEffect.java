package pl.edu.agh.panda5.player.powerups;

public interface PowerUpEffect {
    void applyEffect();
    void decorate(PowerUpDecorator decorator);
}
