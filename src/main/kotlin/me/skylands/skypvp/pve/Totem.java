package me.skylands.skypvp.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.task.pve.BossAttackTask;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            LivingEntity entity = (LivingEntity) world.spawnEntity(spawnLocation, enemies.get(getRandomNumber(0, enemies.size()-1)));
            entity.setRemoveWhenFarAway(true);
            entity.setMaxHealth(20 + 10*difficulty);
            entity.setHealth(entity.getMaxHealth());
            entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, -1 + difficulty, false, false));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, -1 + difficulty, false, false));
        }
    }

    public void spawnBoss() {
        Location spawnLocation = getRandomSpawnLocation();

        switch (this.bossType) {
            case "Slime":
                // SPAWN CONDITIONS SUPPOSED TO BE CHECKED FOR @ TotemEnemiesSpawnTask
                BossSlime bossSlime = new BossSlime(spawnLocation);
                bossSlime.setPosition(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
                Helper.bossData.put(this.totemIdentifier, new BossData(true, bossSlime.getBukkitEntity(), this.getRandomSpawnLocation()));

                // AttackTask SUPPOSED TO BE STARTED @ BOSS CLASS (or here? todo)

                Bukkit.getScheduler().runTaskTimer(SkyLands.plugin, new BossAttackTask(bossSlime.getBukkitEntity(), bossSlime.getBossAttack()), 20L, 20L);

                Bukkit.getLogger().info("Task started. Boss Attack: " + bossSlime.getBossAttack().toString());
                break;
            /*[...]":*/
        }
    }

    private Location getRandomSpawnLocation() {
        return new Location(
                world,
                centerLocation.getX() + getRandomNumber(-spawnRadius, spawnRadius),
                centerLocation.getY(),
                centerLocation.getZ() + getRandomNumber(-spawnRadius, spawnRadius)
        );
    }

    private int getSpawnAmount() {
        int nearbyPlayers = getNearbyPlayersAmount();
        if (nearbyPlayers == 0 || getNearbyMonstersAmount() > 10) {
            clearMobs();
            return 0;
        }
        if (nearbyPlayers < 3) {
            return getRandomNumber(1, 4);
        } else {
            return getRandomNumber(nearbyPlayers / 2, nearbyPlayers * 2);
        }
    }

    private int getNearbyPlayersAmount() {
        int count = 0;
        for (Player player : centerLocation.getWorld().getPlayers()) {
            if (player.getLocation().distance(centerLocation) < (spawnRadius * 3)) {
                count++;
            }
        }
        return count;
    }

    private int getNearbyMonstersAmount() {
        int count = 0;
        for (LivingEntity livingEntity : centerLocation.getWorld().getLivingEntities()) {
            if (livingEntity instanceof Player) continue;
            if (livingEntity.getLocation().distance(centerLocation) < (spawnRadius * 3)) {
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
            if (livingEntity.getLocation().distance(centerLocation) < (spawnRadius * 7.5)) {
                if (enemies.contains(livingEntity.getType())) {
                    if (getRandomNumber(0, 10) < 4) {
                        livingEntity.remove();
                    }
                }
            }
        }
    }

    public int getTotemIdentifier() {
        return this.totemIdentifier;
    }
}