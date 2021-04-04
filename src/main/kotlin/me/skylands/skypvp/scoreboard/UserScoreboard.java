package me.skylands.skypvp.scoreboard;

import me.skylands.skypvp.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserScoreboard extends DynamicScoreboard {

    public UserScoreboard() {
        super("§6§lSkyLands");

        super.addStaticLine("§7Online", 8);
        super.addDynamicLine("online", "§e", "-", 7);
        super.addStaticLine("§7Kills", 6);
        super.addDynamicLine("kills", "§e", "-", 5);
        super.addStaticLine("§7Tode", 4);
        super.addDynamicLine("deaths", "§e", "-", 3);
        super.addStaticLine("§7Level", 2);
        super.addDynamicLine("level", "§e", "-", 1);
    }

    public void update(User user, Player player) {
        super.updateLine("online", " §e► §c" + Bukkit.getOnlinePlayers().size());
        super.updateLine("kills", " §e► §c" + user.getKills());
        super.updateLine("deaths", " §e► §c" + user.getDeaths());
        super.updateLine("level", " §e► §c" + player.getLevel());
    }

}
