package me.skylands.skypvp.listener;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.nms.ActionBar;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillstreakListener implements Listener {

    private final Helper helper = new Helper();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        UserService userService = SkyLands.userService;
        Player victim = event.getEntity();
        User victimUser = userService.getUser(victim);
        Player killer = victim.getKiller();

        if(killer == null) {
            victimUser.setCurrentKillstreak(0);
            return;
        }

        User killerUser = userService.getUser(killer);
        int victimKillstreak = victimUser.getCurrentKillstreak();

        if(victimKillstreak > 10 && victimKillstreak < 30) {
            int levelToAdd = (int)Math.ceil( 0.40 * victimKillstreak);
            killer.sendMessage(Messages.PREFIX + "Du hast die §eKillstreak§7 von §e" + victim.getName() + " §7beendet! §7+§a" + levelToAdd + " Level");
            killer.setLevel(killer.getLevel() + levelToAdd);
        } else if(victimKillstreak > 30) {
            int pointsToAdd = (int)Math.ceil( 0.20 * victimKillstreak);
            killer.sendMessage(Messages.PREFIX + "Du hast die §eKillstreak§7 von §e" + victim.getName() + " §7beendet! §7+§c" + pointsToAdd + " Blutpunkte");

            if(helper.hasConverterPotion(killer.getName())) {
                killer.setLevel(killer.getLevel() + pointsToAdd);
                ActionBar.INSTANCE.send("§aUmgewandelt§7! Du hast §a" + pointsToAdd + "Level§7 erhalten", killer);
                return;
            }
            killerUser.setBloodPoints(killerUser.getBloodPoints() + pointsToAdd);
        }

        victimUser.setCurrentKillstreak(0);
        killerUser.setCurrentKillstreak(killerUser.getCurrentKillstreak() + 1);

        if(killerUser.getCurrentKillstreak() % 10 == 0 && !(killerUser.getCurrentKillstreak() % 20 == 0)) {
            if(killerUser.getCurrentKillstreak() == 50) {
                killer.sendMessage(Messages.PREFIX + "Du hast eine Killstreak von §e" + killerUser.getCurrentKillstreak() + " §7erreicht!");
                killer.sendMessage(Messages.PREFIX + "Du erhältst von nun an §edoppelte Level§7 für jeden Kill.");
            } else {
                killer.sendMessage(Messages.PREFIX + "Du hast eine Killstreak von §e" + killerUser.getCurrentKillstreak() + " §7erreicht! +§a20 Level");
                killer.setLevel(killer.getLevel() + 20);
            }
            Bukkit.broadcastMessage(Messages.PREFIX + "§e" + killer.getName() + " §7hat eine Killstreak von §e" + killerUser.getCurrentKillstreak() + "§7 erreicht!");

        } else if(killerUser.getCurrentKillstreak() % 20 == 0) {
            killer.sendMessage(Messages.PREFIX + "Du hast eine Killstreak von §e" + killerUser.getCurrentKillstreak() + " §7erreicht! +§c2 Blutpunkte");
            if(helper.hasConverterPotion(killer.getName())) {
                killer.setLevel(killer.getLevel() + 2);
                ActionBar.INSTANCE.send("§aUmgewandelt§7! Du hast §a2 Level§7 erhalten", killer);
                return;
            }
            killerUser.setBloodPoints(killerUser.getBloodPoints() + 2);
        }
    }
}
