package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.Clans;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.entity.Player;

@CommandData(name = "create", args = "<clan-tag> <clan-name>", description = "Clan erstellen", strictPermission = true)
public class CommandCreate extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 2) {
            String tag = args[0];
            String name = args[1];
            int checkTag = Clans.isValidTag(tag);
            int checkName = Clans.isValidName(name);

            if (checkTag == 0 && checkName == 0) {
                if (!ClanManager.existClan(tag)) {
                    p.sendMessage(Message.format(Message.CREATE_SUCCESSFUL, name, tag));
                    ClanManager.createClan(tag, name, p);
                } else {
                    p.sendMessage(Message.CREATE_ALREADY_TAKEN);
                }
            } else {
                if (checkTag == 1) {
                    p.sendMessage(
                            Message.format(Message.CREATE_INVALID_LENGTH, "Clan-Tag", "3", "5"));
                } else if (checkTag == 2) {
                    p.sendMessage(Message.format(Message.CREATE_INVALID_NAME, "Clan-Tag"));
                }
                if (checkName == 1) {
                    p.sendMessage(
                            Message.format(Message.CREATE_INVALID_LENGTH, "Clan-Name", "3", "16"));
                } else if (checkName == 2) {
                    p.sendMessage(Message.format(Message.CREATE_INVALID_NAME, "Clan-Name"));
                }
            }
        } else {
            sendCommandHelp(p);
        }
    }

}