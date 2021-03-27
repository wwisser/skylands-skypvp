package me.skylands.skypvp.listener;

import me.skylands.skypvp.nms.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// TODO refactor legacy code
public class EntityDamageByEntityListenerLegacy implements Listener {

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Projectile) {
            final Projectile bullit = (Projectile) ev.getDamager();
            if (bullit.getShooter() instanceof Player && ev.getEntity() instanceof Player) {
                final Player pl = (Player) ev.getEntity();
                final Player to = (Player) bullit.getShooter();
                showHeal(pl, to);
            }
        }
        if (ev.getDamager() instanceof Player && ev.getEntity() instanceof Player) {
            final Player pl2 = (Player) ev.getEntity();
            final Player to2 = (Player) ev.getDamager();
            showHeal(pl2, to2);
        }
    }

    private void showHeal(final Player entity, final Player receiver) {
        final double maxHealth = entity.getMaxHealth();

        double health = entity.getHealth();
        if (health < 0.0 || entity.isDead()) {
            health = 0.0;
        }

        final StringBuilder style = new StringBuilder();
        int left = this.getLimitHealth(maxHealth);
        final double heart = maxHealth / this.getLimitHealth(maxHealth);
        final double halfHeart = heart / 2.0;
        double tempHealth = health;
        if (maxHealth != health && health >= 0.0 && !entity.isDead()) {
            for (int i = 0; i < this.getLimitHealth(maxHealth) && tempHealth - heart > 0.0; ++i) {
                tempHealth -= heart;
                style.append("§a|");
                --left;
            }
            if (tempHealth > halfHeart) {
                style.append("§a|");
                --left;
            } else if (tempHealth > 0.0 && tempHealth <= halfHeart) {
                style.append("§e:");
                --left;
            }
        }
        if (maxHealth != health) {
            for (int i = 0; i < left; ++i) {
                style.append("§c.");
            }
        } else {
            for (int i = 0; i < left; ++i) {
                style.append("§a|");
            }
        }

        ActionBar.INSTANCE.send("§6" + entity.getName() + " §7[" + style + "§7]", receiver);
    }

    private int getLimitHealth(final double maxHealth) {
        return (int) maxHealth;
    }

}
