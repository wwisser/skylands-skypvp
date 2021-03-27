package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "leave", description = "Verlasse einen Clan", permission = ClanRank.MEMBER)
public class CommandLeave extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (!clanUser.getRank().equals(ClanRank.OWNER)) {
            Clan clan = clanUser.getClan();
            clan.removeMember(p.getUniqueId().toString());
            clan.broadcastMessage(Message.format(Message.LEAVE_BROADCAST, p.getName()));
            p.sendMessage(Message.format(Message.LEAVE_LEFT, clan.getName()));
        } else {
            p.sendMessage(Message.LEAVE_CANT);
        }
    }

}