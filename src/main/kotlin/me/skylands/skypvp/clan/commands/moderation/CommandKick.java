package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandData(name = "kick", args = "<spieler>", description = "Kicke Clan Mitglieder", permission = ClanRank.MODERATOR)
public class CommandKick extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            String uuid = ModuleUUID.getUuidByName(args[0]);

            if (uuid != null) {
                if (ClanManager.hasClan(uuid)) {
                    if (ClanManager.getClanTag(uuid).equals(ClanManager.getClanTag(p))) {
                        ClanUser target = ClanManager.getClanUserByUuid(uuid);

                        if (target.getRank().getId() < clanUser.getRank().getId()) {
                            Clan clan = clanUser.getClan();
                            Player targetPlayer = Bukkit.getPlayer(args[0]);

                            clan.removeMember(uuid);
                            clan.broadcastMessage(
                                    Message.format(Message.KICK_BROADCAST, target.getName(),
                                            clanUser.getRank().getColor() + p.getName()));
                            if (targetPlayer != null && targetPlayer.isOnline()) {
                                targetPlayer.sendMessage(Message.KICK_KICKED);
                            }
                        } else {
                            p.sendMessage(Message.KICK_NOT_ALLOWED);
                        }
                    } else {
                        p.sendMessage(Message.KICK_NOT_CLAN);
                    }
                } else {
                    p.sendMessage(Message.KICK_NOT_CLAN);
                }
            } else {
                p.sendMessage(Message.KICK_NOT_FOUND);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}