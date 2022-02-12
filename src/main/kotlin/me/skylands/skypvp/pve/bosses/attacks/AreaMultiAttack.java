package me.skylands.skypvp.pve.bosses.attacks;

import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class AreaMultiAttack implements BossAttack {

    private final BossTracker bossTracker = new BossTracker();

    @Override
    public void preAttack(Player player, CraftEntity boss) {
        player.sendMessage("PreAttackEvent called on you");

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

                ((Player) entry).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*11, 3, true, false));
                entry.sendMessage("§a§lGIFTATTACKE! §r§eDu wurdest vergiftet.");
            }
        }

        Vector bBoost = boss.getLocation().getDirection();
        bBoost.setY(bBoost.getY() + 3);
        boss.setVelocity(bBoost);

        boss.getWorld().createExplosion(boss.getLocation().getX(), boss.getLocation().getY(), boss.getLocation().getZ(), 4f, false, false);
    }

    @Override
    public void postAttack(Player player, CraftEntity boss) {
        ItemStack itemStack = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 20 * 5, 3), true);
        itemStack.setItemMeta(potionMeta);
        Location loc = boss.getLocation().clone();
        loc.setY(loc.getY() + 4);
        ThrownPotion thrownPotion = (ThrownPotion) boss.getWorld().spawnEntity(boss.getLocation(), EntityType.SPLASH_POTION);
        thrownPotion.setItem(itemStack);
    }
}
