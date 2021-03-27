package me.skylands.skypvp.clan.listener;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent ev) {
        Bukkit.getScheduler().runTaskAsynchronously(SkyLands.plugin, new Runnable() {
            @Override
            public void run() {
                if (ClanManager.hasClan(ev.getPlayer())) {
                    ClanUser clanUser = ClanManager.getClanUserByPlayer(ev.getPlayer());

                    if (!clanUser.getName().equals(ev.getPlayer().getName())) {
                        clanUser.setName(ev.getPlayer().getName());
                    }
                }
            }
        });
    }

}
