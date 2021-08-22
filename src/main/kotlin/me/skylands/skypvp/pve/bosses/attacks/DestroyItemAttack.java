package me.skylands.skypvp.pve.bosses.attacks;

import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.bosses.Boss;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DestroyItemAttack implements BossAttack {

    private final BossTracker bossTracker = new BossTracker();

    @Override
    public void preAttack(Player player, CraftEntity boss) {
        player.sendMessage("PreAttackEvent called on you");

        // TODO: Display Particles Indicating Incoming Attack (Using BossParticleTask)
    }

    @Override
    public void attack(Player player, CraftEntity boss) {
        player.sendMessage("AttackEvent called on you");
        player.getInventory().remove(player.getInventory().getItemInHand());
    }

    @Override
    public void postAttack(Player player, CraftEntity boss) {
       player.sendMessage("PostAttackEvent called on you");

       int bossID = bossTracker.getIDbyUUID(boss.getUniqueId().toString());

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
