package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "revoke", args = "<spieler>", description = "Ziehe Einladungen zurück", permission = ClanRank.MODERATOR)
public class CommandRevoke extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            String uuid = ModuleUUID.getUuidByName(args[0]);

            if (uuid != null) {
                String clanTag = ClanManager.getClanTag(p);
                if (ClanManager.hasInvitation(uuid, clanTag)) {
                    ClanManager.removeInvitation(uuid, clanTag);
                    p.sendMessage(Message.format(Message.REVOKE_REVOKED, args[0]));
                } else {
                    p.sendMessage(Message.REVOKE_NO_INVITE);
                }
            } else {
                p.sendMessage(Message.REVOKE_NOT_FOUND);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}