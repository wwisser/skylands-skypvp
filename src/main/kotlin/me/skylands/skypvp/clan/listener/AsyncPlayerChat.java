package me.skylands.skypvp.clan.listener;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent ev) {
        Player player = ev.getPlayer();

        if (ClanManager.hasClan(player)) {
            if (ev.getMessage().startsWith("#")) {
                ClanUser clanUser = ClanManager.getClanUserByPlayer(player);
                clanUser.getClan().broadcastMessage(
                        Message.format(Message.CLAN_CHAT,
                                clanUser.getRank().getColor() + player.getName(),
                                ev.getMessage().substring(1)));
                ev.setCancelled(true);
            }
        }
    }

}
