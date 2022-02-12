package me.skylands.skypvp.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.pve.bosses.attacks.AreaMultiAttack;
import me.skylands.skypvp.task.pve.BossAttackTask;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Totem {

    final Location centerLocation;
    final int difficulty;
    final int spawnRadius;
    final List<EntityType> enemies;
    final World world;
    final String bossType;
    final int totemIdentifier;

    public Totem(Location centerLocation, int difficulty, int spawnRadius, List<EntityType> enemies, String bossType, int totemIdentifier) {
        this.centerLocation = centerLocation;
        this.difficulty = difficulty;
        this.spawnRadius = spawnRadius;
        this.enemies = enemies;
        this.bossType = bossType;
        this.totemIdentifier = totemIdentifier;
        world = centerLocation.getWorld();
    }

    public void spawnMobs() {
        int spawnAmount = getSpawnAmount();
        for (int i = 0; i < spawnAmount; i++) {
            Location spawnLocation = getRandomSpawnLocation();
            LivingEntity entity = (LivingEntity) world.spawnEntity(spawnLocation, enemies.get(getRandomNumber(0, enemies.size())));
            entity.setMetadata(Integer.toString(totemIdentifier), new FixedMetadataValue(SkyLands.plugin, difficulty));
            entity.setRemoveWhenFarAway(true);
            entity.setMaxHealth(20 + 10 * difficulty);
            entity.setHealth(entity.getMaxHealth());
            entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, -1 + difficulty, false, false));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, -1 + difficulty, false, false));
        }
    }

    public void spawnBoss() {
        Location spawnLocation = getRandomSpawnLocation();

        if(this.bossType.equals("Slime")) {
            BossSlime bossSlime = new BossSlime(spawnLocation);
            bossSlime.setPosition(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
            Helper.bossData.put(this.totemIdentifier, new BossData(true, bossSlime.getBukkitEntity(), this.getRandomSpawnLocation(), this.centerLocation));

            BukkitRunnable bossAttackTask = new BossAttackTask(bossSlime.getBukkitEntity(), bossSlime.getBossAttack());
            bossAttackTask.runTaskTimer(SkyLands.plugin, 20, 20);

            if(Helper.getDebugMode()) Bukkit.getLogger().info("Slime boss spawned. Selected Boss Attack: " + bossSlime.getBossAttack().toString());
        }
    }

    private Location getRandomSpawnLocation() {
        Location spawnLocation = new Location(
                world,
                centerLocation.getX() + getRandomNumber(-spawnRadius, spawnRadius),
                centerLocation.getY(),
                centerLocation.getZ() + getRandomNumber(-spawnRadius, spawnRadius)
        );

        if (world.getHighestBlockYAt(spawnLocation) <= 10) {
            return getRandomSpawnLocation();
        }

        for (int i = 0; i < 10; i++) {
            if (spawnLocation.getBlock().getType().isSolid()) {
                if (!spawnLocation.add(0, 1, 0).getBlock().getType().isSolid()) {
                    return spawnLocation;
                }
                return getRandomSpawnLocation();
            }
        }

        return spawnLocation;
    }

    private int getSpawnAmount() {
        int nearbyPlayers = getNearbyPlayersAmount();

        int minSpawn = spawnRadius / 5 + nearbyPlayers - 1;
        int maxSpawn = spawnRadius / 4 + nearbyPlayers;

        if (nearbyPlayers == 0) clearMobs();
        if (getNearbyMonstersAmount() > spawnRadius / 3) return 0;
        return getRandomNumber(minSpawn, maxSpawn);
    }

    private int getNearbyPlayersAmount() {
        int count = 0;
        for (Player player : centerLocation.getWorld().getPlayers()) {
            if (player.getLocation().distance(centerLocation) < (spawnRadius * 1.3)) {
                count++;
            }
        }
        return count;
    }

    private int getNearbyMonstersAmount() {
        int count = 0;
        for (LivingEntity livingEntity : centerLocation.getWorld().getLivingEntities()) {
            if (livingEntity instanceof Player) continue;
            if (livingEntity.getLocation().distance(centerLocation) < (spawnRadius * 1.25)) {
                count++;
            }
        }
        return count;
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void clearMobs() {
        for (LivingEntity livingEntity : centerLocation.getWorld().getLivingEntities()) {
            if (livingEntity.getLocation().distance(centerLocation) < (spawnRadius * 1.3)) {
                if (enemies.contains(livingEntity.getType())) {
                    if (getRandomNumber(0, 10) < 4) {
                        livingEntity.remove();
                    }
                }
            }
        }
    }

    public String getBossType() {
        return this.bossType;
    }

    public Location getTotemCenterLocation() {
        return this.centerLocation;
    }

    public Location getCenterLocation() {
        return this.centerLocation;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }

    public int getTotemIdentifier() {
        return this.totemIdentifier;
    }
}