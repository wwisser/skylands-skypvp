package me.skylands.skypvp.pve.bosses;

import me.skylands.skypvp.pve.bosses.attacks.BossAttack;
import me.skylands.skypvp.pve.bosses.attacks.DestroyItemAttack;

public interface Boss {

    public default Integer attackSpeedMultiplier() {
        return 4;
    }

    public default Integer getBossHealth() {
        return 1024;
    }

    public default Integer getFollowRange() {
        return 8;
    }

    public default String getBossPrefix () {
        return "§7[§c§lBOSS§r§7] ";
    }

    public default BossAttack getBossAttack() {
        return new DestroyItemAttack();
    }

    public default Float getDamageReduction() {
        return 0.9F;
    }

    public default Integer getDamageIncrease() {
        return 5;
    }
}
