package me.skylands.skypvp.clan.listener;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent ev) {
        if (!ev.isCancelled()) {
            if (ev.getEntity() instanceof Player) {
                Player entity = (Player) ev.getEntity();
                Player damager = null;

                if (ev.getDamager() instanceof Player) {
                    damager = (Player) ev.getDamager();
                } else if (ev.getDamager() instanceof Projectile) {
                    Projectile projectile = (Projectile) ev.getDamager();

                    if (projectile.getShooter() instanceof Player) {
                        damager = (Player) projectile.getShooter();
                    }
                }

                if (damager != null) {
                    if (!damager.equals(entity)) {
                        if (ClanManager.hasClan(damager) && ClanManager.hasClan(entity)) {
                            if (ClanManager.getClanTag(damager)
                                    .equals(ClanManager.getClanTag(entity))) {
                                damager.sendMessage(Message.FRIENDLYFIRE);
                                ev.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

}