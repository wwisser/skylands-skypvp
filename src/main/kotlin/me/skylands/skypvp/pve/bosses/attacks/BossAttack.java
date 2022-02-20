package me.skylands.skypvp.pve.bosses.attacks;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface BossAttack {

    public void preAttack(Player player, CraftEntity boss);


    public void attack(Player player, CraftEntity boss);


    public void postAttack(Player player, CraftEntity boss);
}
