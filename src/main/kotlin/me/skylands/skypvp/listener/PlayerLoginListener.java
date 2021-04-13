package me.skylands.skypvp.listener;

import me.skylands.skypvp.SkyLands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String ip = player.getAddress().toString().split(":")[0].replace(".", "*").replace("/", "");
        final String labelIps = "ips." + player.getUniqueId().toString();
        final String labelPlayers = "players." + ip;

        final Set<String> ips = SkyLands.ipMatchingService.getEntries(labelIps);
        ips.add(ip);

        final Set<String> players = SkyLands.ipMatchingService.getEntries(labelPlayers);
        players.add(player.getUniqueId().toString());

        SkyLands.ipMatchingService.updateEntries(labelIps, ips);
        SkyLands.ipMatchingService.updateEntries(labelPlayers, players);
    }

}
