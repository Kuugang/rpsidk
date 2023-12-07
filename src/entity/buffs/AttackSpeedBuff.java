package entity.buffs;

import entity.Player;

public class AttackSpeedBuff implements PlayerBuff {
    public double reloadDecrease;

    public AttackSpeedBuff(double reloadDecrease){
        this.reloadDecrease = reloadDecrease;
    }

    @Override
    public void applyBuff(Player player) {
        player.reloadTime -= reloadDecrease;
    }
}
