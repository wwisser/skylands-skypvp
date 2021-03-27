package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "ranking", description = "Zeigt die besten Clans")
public class CommandRanking extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        p.sendMessage("");
        p.sendMessage("  §7Graue Clan Farbe §8- §7unter 200 Kills.");
        p.sendMessage("  §aHellgrüne §7Clan Farbe §8- §7von 200 bis 500 Kills.");
        p.sendMessage("  §2Dunkelgrüne §7Clan Farbe §8- §7von 500 bis 1000 Kills");
        p.sendMessage("  §cHellrote §7Clan Farbe §8- §7von 1000 bis 1500 Kills.");
        p.sendMessage("  §4Rote §7Clan Farbe §8- §7von 1500 bis 2000 Kills.");
        p.sendMessage("  §bHellblaue §7Clan Farbe §8- §7von 2000 bis 3000 Kills.");
        p.sendMessage("  §6§lGoldene §7Clan Farbe §8- §7über 3000 Kills.");
    }

}
