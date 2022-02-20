package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.nms.ActionBar;
import me.skylands.skypvp.pve.BossCache;
import me.skylands.skypvp.pve.data.BossData;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.task.pve.SpawnChestTask;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Bukkit;
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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PvEBossListener implements Listener {

    private final String[] couponItemLore = {" ", "§eRechtsklick, um Gutschein einzulösen"};
    private final ItemStack bpCoupon = new ItemBuilder(Material.REDSTONE).name("§cBlutpunkt").modifyLore().set(couponItemLore).finish().glow().build();

    private final UserService userService = SkyLands.userService;
    private final BossCache bossCache = new BossCache();
    private final Helper helper = new Helper();

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

                if(Helper.getSilentRemovalStatus()) {
                    helper.setDataStatus(0);
                    return;
                }

                BossTracker bossTracker = new BossTracker();
                bossTracker.createBossHologram(bossTracker.getIDbyUUID(uuidAsString));

                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.LEVEL_UP, 20, 20);
                bossTracker.cancelParticleTask(bossTracker.getIDbyUUID(uuidAsString), false);

                for(Entity entityNearby : event.getEntity().getNearbyEntities(30, 5, 30)) {
                    if(entityNearby instanceof Player) {
                        ((Player) entityNearby).sendTitle("§c§lBoss tot!", "§eGlückwunsch");
                    }
                }

                helper.setDataStatus(0);
                helper.setUnprocessedData(bossCache.fetchResults());
                helper.processData();
                bossCache.reset();

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

                bossCache.setDamageDealt(attackingPlayer.getName(), bossCache.getDamageDealt(attackingPlayer.getName()) + event.getFinalDamage());

                if(bossCache.getConsecutiveHits(attackingPlayer.getName()) >= 3) {
                    attackingPlayer.sendMessage("§b§lFREEZE§r§7! Der §aSlimekönig§7 hat dich §eeingefroren§7.");
                    if(!Helper.getFrozenPlayers().contains(attackingPlayer.getName())) {
                        Helper.freeze(attackingPlayer.getName());

                        Bukkit.getScheduler().runTaskLater(SkyLands.plugin, () -> {
                            Helper.unfreeze(attackingPlayer.getName());
                        }, 35L);
                    }
                }
            }
        }

        if(attackedEntity instanceof Player && !(attackingPlayer instanceof Player)) {
            if(craftEntityAttacker instanceof Boss) {
                event.setDamage(event.getDamage() * ((Boss) craftEntityAttacker).getDamageIncrease());

                bossCache.setConsecutiveHits(attackedEntity.getName(), 0);
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

                            Vector boost = pShooter.getLocation().getDirection();
                            boost.setX(boost.getX());
                            boost.setZ(boost.getZ());
                            boost.setY(1.4);
                            pShooter.setVelocity(boost);

                            Bukkit.getScheduler().runTaskLater(SkyLands.plugin, () -> {
                                moveToward(pShooter, attackedEntity.getLocation(), .9);
                            }, 20L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand().getType().equals(bpCoupon.getType())) {
                if (hasItem(player, bpCoupon, 1)) {
                    player.getInventory().removeItem(bpCoupon);
                    if (!helper.hasConverterPotion(player.getName())) {
                        userService.getUser(player).setBloodPoints(userService.getUser(player).getBloodPoints() + 1);
                    } else {
                        ActionBar.INSTANCE.send("§aUmgewandelt§7! Du hast §a1 Level§7 erhalten", player);
                        player.setLevel(player.getLevel() + 1);
                    }
                    player.sendMessage(Messages.PREFIX + "Du hast §ceinen Blutpunkt§7 erhalten. Du hast nun §c" + userService.getUser(player).getBloodPoints() + " Blutpunkte§7.");
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
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
        if(event.getWorld() == SkyLands.WORLD_SKYPVP) event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for(String entry : Helper.getFrozenPlayers()) {
            if(entry.equals(player.getName())) {
                Location location = event.getFrom().clone();
                location.setYaw(event.getTo().getYaw());
                location.setPitch(event.getTo().getPitch());
                event.setTo(location);
            }
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        ItemStack bpPotion = new ItemBuilder(Material.POTION).name("§7Trank der §eUmwandlung").data((short) 2).modifyLore().add(" ").add("§7Für §e10 Minuten §7erhältst du").add("§aLevel§7 anstelle von §cBlutpunkten§7.").finish().build();
        PotionMeta potionMeta = (PotionMeta) bpPotion.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 10 * (60*20),0,true,true), true);
        potionMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
        bpPotion.setItemMeta(potionMeta);

        if(item.equals(bpPotion)) {
            if(helper.hasConverterPotion(player.getName())) {
                player.sendMessage(Messages.PREFIX + "Trank §ebereits aktiv§7!");
                event.setCancelled(true);
            } else {
                helper.addPlayerToConverterPotion(player.getName());
                Bukkit.getScheduler().runTaskLater(SkyLands.plugin, () -> {
                    helper.removePlayerFromConverterPotion(player.getName());
                }, 10 * (20*60L));
                player.sendMessage(Messages.PREFIX + "§eUmwandlungstrank §aaktiviert§7!");
            }
        }
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
