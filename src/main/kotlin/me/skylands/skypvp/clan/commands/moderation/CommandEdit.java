package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.inventory.InventoryEdit;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanAccess;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "edit", description = "Editiere die Clan Einstellungen", permission = ClanRank.ADMIN)
public class CommandEdit extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length != 2) {
            InventoryEdit.open(p, clanUser);
        } else {
            Clan clan = clanUser.getClan();
            String key = args[0];
            String value = args[1];

            if (key.equals("access")) {
                if (value.equals("open")) {
                    clan.setAccess(ClanAccess.OPEN);
                    p.sendMessage(
                            Message.format(Message.EDIT_CHANGED_ACCESS, ClanAccess.OPEN.getName()));
                } else if (value.equals("invite_only")) {
                    clan.setAccess(ClanAccess.INVITE_ONLY);
                    p.sendMessage(Message.format(Message.EDIT_CHANGED_ACCESS,
                            ClanAccess.INVITE_ONLY.getName()));
                } else if (value.equals("closed")) {
                    clan.setAccess(ClanAccess.CLOSED);
                    p.sendMessage(Message.format(Message.EDIT_CHANGED_ACCESS,
                            ClanAccess.CLOSED.getName()));
                } else {
                    sendCommandHelp(p);
                }
            } else {
                sendCommandHelp(p);
            }
        }
    }

}