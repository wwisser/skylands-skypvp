package me.skylands.skypvp.clan.listener;

import me.skylands.skypvp.clan.util.clan.ClanManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent ev) {
        Player entity = ev.getEntity();
        Player killer = entity.getKiller();

        if (killer != null && killer.isOnline()) {
            if (ClanManager.hasClan(killer)) {
                ClanManager.getClanUserByPlayer(killer).addKill();
            }
            if (ClanManager.hasClan(entity)) {
                ClanManager.getClanUserByPlayer(entity).addDeath();
            }
        }
    }

}
