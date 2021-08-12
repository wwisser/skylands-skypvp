package me.skylands.skypvp.pve.bosses.attacks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface BossAttack {


    public void preAttack(Player player, Entity boss);


    public void attack(Player player, Entity boss);


    public void postAttack(Player player, Entity boss);
}
