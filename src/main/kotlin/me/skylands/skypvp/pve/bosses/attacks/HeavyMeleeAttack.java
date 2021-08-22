package me.skylands.skypvp.pve.bosses.attacks;

import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HeavyMeleeAttack implements BossAttack {

    private BossTracker bossTracker = new BossTracker();

    @Override
    public void preAttack(Player player, CraftEntity boss) {
        player.sendMessage("PreAttackEvent called");
        // TODO PARTICLES!
    }

    @Override
    public void attack(Player player, CraftEntity boss) {
        boss.teleport(player.getLocation());
        player.setHealth(player.getHealth() / 2.5);
    }

    @Override
    public void postAttack(Player player, CraftEntity boss) {
        int bossID = bossTracker.getIDbyUUID(boss.getUniqueId().toString());
        bossTracker.teleportBossToTotem(bossID);

        if(bossID != 0) {
            bossTracker.teleportBossToTotem(bossID);
            CraftEntity handle = bossTracker.getBossHandle(bossID);
            if(handle instanceof LivingEntity) {
                ((LivingEntity) handle).setHealth(((LivingEntity) handle).getMaxHealth());
            } else {
                Bukkit.getLogger().info("can't cast to livingentity");
            }
        }
    }
}
