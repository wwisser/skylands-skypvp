package me.skylands.skypvp.clan.util.command;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {

    private static ArrayList<ClanCommand> commands = new ArrayList<>();

    public static void registerCommand(ClanCommand clanCommand) {
        commands.add(clanCommand);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.NOT_PLAYER);
            return true;
        }
        Player p = (Player) commandSender;
        ClanUser clanUser = ClanManager.getClanUserByPlayer(p);
        ClanRank clanRank = (clanUser != null) ? clanUser.getRank() : ClanRank.NO_CLAN;

        if (args.length == 0) {
            p.sendMessage(Message.PREFIX_LONG);
            for (ClanCommand clanCommand : commands) {
                CommandData commandData = clanCommand.getClass().getAnnotation(CommandData.class);
                if ((commandData.strictPermission() && commandData.permission() == clanRank) || (
                        !commandData.strictPermission() && (
                                commandData.permission().getPermissionLevel() <= clanRank
                                        .getPermissionLevel() || !commandData
                                        .hideNoPermission()))) {
                    p.sendMessage(Message.format(Message.DEFAULT_COMMAND_LINE, commandData.name(),
                            commandData.args(), commandData.description()));
                }
            }
            p.sendMessage(Message.PREFIX + "§e#<nachricht> §8- §7Clan-Chat");
            p.sendMessage(Message.DEFAULT_TAGINFO);
            return true;
        } else {
            ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
            arguments.remove(0);

            for (ClanCommand clanCommand : commands) {
                CommandData commandData = clanCommand.getClass().getAnnotation(CommandData.class);
                if (commandData.name().equalsIgnoreCase(args[0])) {
                    try {
                        if ((commandData.strictPermission() && commandData.permission() == clanRank)
                                || (!commandData.strictPermission() && (
                                commandData.permission().getPermissionLevel() <= clanRank
                                        .getPermissionLevel()))) {
                            clanCommand.run(p, clanUser,
                                    arguments.toArray(new String[arguments.size()]));
                        } else {
                            p.sendMessage(Message.format(Message.NO_CLAN_PERMISSION,
                                    commandData.permission().getColor() + commandData.permission()
                                            .getFullName()));
                        }
                    } catch (Exception e) {
                        p.sendMessage(Message.format(Message.ERROR, e.getMessage()));
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        p.performCommand("clan");

        return true;
    }

}
