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
            final Projectile bullit = (Projectile)ev.getDamager();
            if (bullit.getShooter() instanceof Player && ev.getEntity() instanceof Player) {
                final Player pl = (Player)ev.getEntity();
                final Player to = (Player)bullit.getShooter();
                showHeal(pl, to);
            }
        }
        if (ev.getDamager() instanceof Player && ev.getEntity() instanceof Player) {
            final Player pl2 = (Player)ev.getEntity();
            final Player to2 = (Player)ev.getDamager();
            showHeal(pl2, to2);
        }
    }

    private void showHeal(Player pl, Player to) {
        StringBuilder toPlayer = new StringBuilder();
        if (pl.getHealthScale() % 2.0 != 0.0) {
            for (int i = 0; i < (pl.getHealthScale() - 1.0) / 2.0; ++i) {
                toPlayer.append("|");
            }
            toPlayer.append(":");
        }
        else {
            for (int i = 0; i < pl.getHealthScale() / 2.0; ++i) {
                toPlayer.append("|");
            }
        }
        ChatColor prefix;
        if (toPlayer.length() > 7) {
            prefix = ChatColor.GREEN;
        }
        else if (toPlayer.length() > 4) {
            prefix = ChatColor.YELLOW;
        }
        else {
            prefix = ChatColor.RED;
        }
        ActionBar.INSTANCE.send( "§6" + pl.getName() + " §7[" + prefix + toPlayer + "§7]", to);
    }

}
