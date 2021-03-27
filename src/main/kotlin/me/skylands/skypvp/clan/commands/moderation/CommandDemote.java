package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "demote", args = "<spieler>", description = "Degradiere Clan Mitglieder", permission = ClanRank.ADMIN)
public class CommandDemote extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            String uuid = ModuleUUID.getUuidByName(args[0]);

            if (uuid != null) {
                if (ClanManager.hasClan(uuid)) {
                    if (ClanManager.getClanTag(uuid).equals(ClanManager.getClanTag(p))) {
                        ClanUser target = ClanManager.getClanUserByUuid(uuid);

                        int newRankId = target.getRank().getId() - 1;
                        if (newRankId >= 1 && (target.getRank().getId() < clanUser.getRank()
                                .getId())) {
                            ClanRank newRank = ClanRank.getRankById(newRankId);
                            target.setRank(newRank);
                            clanUser.getClan().broadcastMessage(
                                    Message.format(Message.DEMOTE_BROADCAST, target.getName(),
                                            clanUser.getRank().getColor() + p.getName(),
                                            newRank.getColor() + newRank.getFullName()));
                        } else {
                            p.sendMessage(Message.DEMOTE_MAX);
                        }
                    } else {
                        p.sendMessage(Message.DEMOTE_NOT_CLAN);
                    }
                } else {
                    p.sendMessage(Message.DEMOTE_NOT_CLAN);
                }
            } else {
                p.sendMessage(Message.DEMOTE_NOT_FOUND);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}