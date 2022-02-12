package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.BossData;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.task.pve.SpawnChestTask;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.util.Vector;

public class PvEListener implements Listener {
    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent event) {
        if (((CraftEntity) event.getEntity()).getHandle() instanceof BossSlime) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();
        String uuidAsString = entity.getUniqueId().toString();

        for(Integer bossID: Helper.bossData.keySet()) {
            CraftEntity handle = Helper.bossData.get(bossID).getBossHandle();

            if(handle.getUniqueId().toString().equals(uuidAsString)) {
                Helper.bossData.replace(bossID, Helper.bossData.get(bossID), new BossData(false, Helper.bossData.get(bossID).getBossHandle(), Helper.bossData.get(bossID).getRandomSpawnLocation(), Helper.bossData.get(bossID).getTotemCenterLocation()));

                if(Helper.getSilentRemovalStatus()) return;

                BossTracker bossTracker = new BossTracker();
                Helper helper = new Helper();

                helper.createBossHologram(bossTracker.getIDbyUUID(uuidAsString));

                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.LEVEL_UP, 20, 20);
                bossTracker.cancelParticleTask(bossTracker.getIDbyUUID(uuidAsString), false);

                for(Entity entityNearby : event.getEntity().getNearbyEntities(30, 5, 30)) {
                    if(entityNearby instanceof Player) {
                        ((Player) entityNearby).sendTitle("§c§lBoss tot!", "§eGlückwunsch");
                    }
                }

                SpawnChestTask spawnChestTask = new SpawnChestTask(bossID);
                spawnChestTask.runTaskTimer(SkyLands.plugin, 0L, 20L);
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

        if((!(attackedEntity instanceof Player)) && event.getDamager() instanceof Projectile) {
            if(craftEntityAttacked instanceof Boss) {
                Projectile projectile = (Projectile) event.getDamager();
                event.setDamage(event.getDamage() - event.getDamage() * ((Boss) craftEntityAttacked).getDamageReduction());

                if(projectile instanceof Arrow) {
                    if(projectile.getShooter() instanceof Player) {
                        double randomDouble = Math.random();
                        if(randomDouble < 0.5) {
                            Player pShooter = ((Player)projectile.getShooter());
                            pShooter.sendMessage("§2§lSLIME-ATTACKE! §eDer Slimekönig wehrt sich.");
                            pShooter.setFireTicks(80);
                            moveToward(pShooter, attackedEntity.getLocation(), 5);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity attackedEntity = event.getEntity();
        net.minecraft.server.v1_8_R3.Entity craftEntityAttacked = ((CraftEntity) event.getEntity()).getHandle();

        if(event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (!(event.getEntity() instanceof Player)) {
                if (craftEntityAttacked instanceof Boss) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }

    public void moveToward(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);
    }
}
