package pl.edu.agh.panda5.player.powerups;

public class PowerUpCoinMagnet extends PowerUpDecorator {
    @Override
    public void applyEffect(){
        System.out.println("Magneting coins");
    }
}
