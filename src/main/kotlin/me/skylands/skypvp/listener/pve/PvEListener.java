package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.BossData;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.task.pve.SpawnChestTask;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PvEListener implements Listener {

    private final ItemStack bpCoupon = new ItemBuilder(Material.REDSTONE).name("§cBlutpunkt").modifyLore().add(" ").add("§eRechtsklick, um Gutschein einzulösen").finish().glow().build();
    private final UserService userService = SkyLands.userService;

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
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand().equals(bpCoupon)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (hasItem(player, bpCoupon, 1)) {
                    player.getInventory().removeItem(bpCoupon);
                    userService.getUser(player).setBloodPoints(userService.getUser(player).getBloodPoints() + 1);
                    player.sendMessage(Messages.PREFIX + "Du hast §ceinen Blutpunkt§7 erhalten. Du hast nun §c" + userService.getUser(player).getBloodPoints() + " Blutpunkte§7.");
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity attackedEntity = event.getEntity();
        net.minecraft.server.v1_8_R3.Entity craftEntityAttacked = ((CraftEntity) event.getEntity()).getHandle();

        if (event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
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

    private boolean hasItem(Player player, ItemStack query, int amount) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack != null) {
                if(itemStack.getType().equals(query.getType()) && itemStack.getItemMeta().equals(query.getItemMeta())) {
                    count += itemStack.getAmount();
                }
            }
        }
        return (count >= amount);
    }

    private void moveToward(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);
    }
}
