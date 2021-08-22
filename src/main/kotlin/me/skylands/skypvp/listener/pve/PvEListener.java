package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.BossData;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.task.pve.RemoveChestTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.*;

public class PvEListener implements Listener {

    private final Helper entityManager = new Helper();

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent event) {
        if (((CraftEntity) event.getEntity()).getHandle() instanceof BossSlime) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        String uuidAsString = entity.getUniqueId().toString();

        for(Integer bossID: Helper.bossData.keySet()) {
            CraftEntity handle = Helper.bossData.get(bossID).getBossHandle();

            if(handle.getUniqueId().toString().equals(uuidAsString)) {
                Helper.bossData.replace(bossID, Helper.bossData.get(bossID), new BossData(false, Helper.bossData.get(bossID).getBossHandle(), Helper.bossData.get(bossID).getSpawnLocation()));

                entity.getLocation().getWorld().getBlockAt(entity.getLocation()).setType(Material.CHEST);
                event.getEntity().getKiller().sendMessage("You killed the boss! Chest should be there by now.");

                Block chest = entity.getLocation().getWorld().getBlockAt(entity.getLocation());
                BlockState state = chest.getState();

                // TODO Add actual rewards
                if(state instanceof Chest) {
                    for(int i = 0; i < ((Chest) state).getBlockInventory().getSize(); i++) {
                        ((Chest) state).getBlockInventory().setItem(i, new ItemBuilder(Material.FEATHER).name("Cool feather!").build());
                    }
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyLands.plugin, new RemoveChestTask(entity.getLocation()), 20 * 30);
                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.EXPLODE, 20, 20);
                // TODO Fill it up! Boooom Sound! Tbc
                // TODO CANCEL EXPLOSION BLOCK DAMAGE & REGULAR DAMAGE TODO tbc

                for(Entity entityNearby : event.getEntity().getNearbyEntities(8, 8, 8)) {
                    if(entityNearby instanceof Player) {
                        ((Player) entityNearby).sendTitle("§c§lBoss died!", "§6§lCongratz!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity attackedEntity = event.getEntity();
        Entity attackingPlayer = event.getDamager();
        net.minecraft.server.v1_8_R3.Entity craftEntityAttacked = ((CraftEntity) event.getEntity()).getHandle();
        net.minecraft.server.v1_8_R3.Entity craftEntityAttacker = ((CraftEntity) event.getDamager()).getHandle();


        if(attackingPlayer instanceof Player && !(attackedEntity instanceof Player)) {
            if (craftEntityAttacked instanceof Boss) {
                event.setDamage(event.getDamage() - event.getDamage() * ((Boss) craftEntityAttacked).getDamageReduction());
            }
        }

        if(attackedEntity instanceof Player && !(attackingPlayer instanceof Player)) {
            if(craftEntityAttacker instanceof Boss) {
                event.setDamage(event.getDamage() * ((Boss) craftEntityAttacker).getDamageIncrease());
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            if(!(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
            }
        }
    }
}
