package me.skylands.skypvp.pve.bosses.attacks;

import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ExplosionAttack implements BossAttack {

    private final BossTracker bossTracker = new BossTracker();

    /**
     * Particles indicating an upcoming attack (todo)
     *
     * Boss should pay more attention towards the player (boost?) (todo)
     */
    @Override
    public void preAttack(Player player, CraftEntity boss) {
        player.sendMessage("PreAttackEvent called");
        // TODO: Particles!
    }

    /**
     * Teleport the boss to the player's location (replace with boost?)
     *
     * Create an explosion
     *
     * Boost the player into the air
     */

    @Override
    public void attack(Player player, CraftEntity boss) {
        boss.teleport(player.getLocation());

        Vector boost = player.getLocation().getDirection();
        boost.setY(boost.getY() + 5);
        player.setVelocity(boost);


        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 4.0f, false, false);
    }

    /**
     *Heal 1/3
     */
    @Override
    public void postAttack(Player player, CraftEntity boss) {
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
