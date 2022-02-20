package me.skylands.skypvp.pve.bosses.attacks;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class AreaMultiAttack implements BossAttack {

    private final BossTracker bossTracker = new BossTracker();
    private final Particles_1_8 particles = SkyLands.particleAPI.getParticles_1_8();

    @Override
    public void preAttack(Player player, CraftEntity boss) {
        bossTracker.displayAttackParticles(bossTracker.getIDbyUUID(boss.getUniqueId().toString()));
    }

    @Override
    public void attack(Player player, CraftEntity boss) {

       bossTracker.cancelParticleTask(bossTracker.getIDbyUUID(boss.getUniqueId().toString()), true);

        List<Entity> nearbyEntites = boss.getNearbyEntities(20, 5, 20);

        for(Entity entry : nearbyEntites) {
            if(entry instanceof Player && !(((Player) entry).getGameMode().equals(GameMode.CREATIVE))) {
                ((Player) entry).setHealth(((Player) entry).getHealth() - 6);

                Vector boost = entry.getLocation().getDirection();
                boost.setX(boost.getX() + 1);
                boost.setZ(boost.getZ() + 1);
                boost.setY(boost.getY() + 1.5);
                entry.setVelocity(boost);
            }
        }

        Vector bBoost = boss.getLocation().getDirection();
        bBoost.setY(3);
        boss.setVelocity(bBoost);

        boss.getWorld().createExplosion(boss.getLocation().getX(), boss.getLocation().getY(), boss.getLocation().getZ(), 4f, false, false);
    }

    @Override
    public void postAttack(Player player, CraftEntity boss) {

        Slime slime = (Slime) boss;
        slime.setHealth(slime.getMaxHealth());

        showParticles(boss);
    }

    private void showParticles(CraftEntity boss) {
        for(int y = 3; y <= 4; y++) {
            for(int x = 0; x <= 3; x++) {
                for(int z = 0; z <= 3; z++) {

                    Location loc = boss.getLocation().clone();
                    loc.setY(loc.getY() + y);
                    loc.setX(loc.getX() + x);
                    loc.setZ(loc.getZ() + z);

                    for (int i = 0; i <= 4; i++) {
                        Object object = particles.HEART().packet(true, loc);
                        particles.sendPacket(loc, 30, object);
                    }
                }
            }
        }
    }
}
