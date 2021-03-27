package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "transfer", args = "<spieler>", description = "Transferiere deinen Clan", permission = ClanRank.OWNER)
public class CommandTransfer extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {

    }

}