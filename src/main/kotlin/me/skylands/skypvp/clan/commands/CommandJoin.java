package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanAccess;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "join", args = "<clan-tag>", description = "Clans beitreten", strictPermission = true)
public class CommandJoin extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            String tag = args[0];

            if (ClanManager.existClan(tag)) {
                Clan clan = ClanManager.getClanByTag(tag);

                if (clan.getClanAccess().equals(ClanAccess.OPEN)) {
                    ClanManager.clearInvitations(p);
                    clan.broadcastMessage(Message.format(Message.JOIN_BROADCAST, p.getName()));
                    clan.addMember(p.getUniqueId().toString());
                    p.sendMessage(Message.format(Message.JOIN_JOINED, clan.getName()));
                } else if (clan.getClanAccess().equals(ClanAccess.INVITE_ONLY)) {
                    if (ClanManager.hasInvitation(p, tag)) {
                        ClanManager.clearInvitations(p);
                        clan.broadcastMessage(Message.format(Message.JOIN_BROADCAST, p.getName()));
                        clan.addMember(p.getUniqueId().toString());
                        p.sendMessage(Message.format(Message.JOIN_JOINED, clan.getName()));
                    } else {
                        p.sendMessage(Message.JOIN_ACCESS_INVITE_ONLY);
                    }
                } else {
                    p.sendMessage(Message.JOIN_ACCESS_PRIVATE);
                }
            } else {
                p.sendMessage(Message.JOIN_NOT_FOUND);
            }
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("decline")) {
                ClanManager.removeInvitation(p, args[0]);
                p.sendMessage(Message.INVITE_DECLINE);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}