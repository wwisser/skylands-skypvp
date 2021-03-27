package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "test", description = "Gibt eine Nachricht aus", permission = ClanRank.MEMBER)
public class CommandTest extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        clanUser.setKills(23);
    }

}
