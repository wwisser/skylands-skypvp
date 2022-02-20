package me.skylands.skypvp.clan;

import me.skylands.skypvp.clan.commands.*;
import me.skylands.skypvp.clan.commands.moderation.*;
import me.skylands.skypvp.clan.inventory.InventoryEdit;
import me.skylands.skypvp.clan.listener.AsyncPlayerChat;
import me.skylands.skypvp.clan.listener.EntityDamageByEntity;
import me.skylands.skypvp.clan.listener.PlayerDeath;
import me.skylands.skypvp.clan.listener.PlayerJoin;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandManager;
import me.skylands.skypvp.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Clans {

    private static Config clans = new Config("plugins/NovaSky/Clan", "clans.yml");
    private static Config clanUserFile = new Config("plugins/NovaSky/Clan", "user.yml");
    private static Config clanInvitations = new Config("plugins/NovaSky/Clan", "invitations.yml");

    private static String allowedTagChars = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
    private static String allowedNameChars = "aäAÄbBcCdDeEfFgGhHiIjJkKlLmMnNoOöÖpPqQrRsStTuUüÜvVwWxXyYzZ0123456789ß-.";

    public void onEnable(JavaPlugin plugin) {
        plugin.getCommand("clan").setExecutor(new CommandManager());

        registerClanCommand(new CommandDisband()); //check
        //registerClanCommand(new CommandTransfer());

        registerClanCommand(new CommandPromote()); //check
        registerClanCommand(new CommandDemote()); //check
        registerClanCommand(new CommandEdit()); //check

        registerClanCommand(new CommandInvite()); //check
        registerClanCommand(new CommandRevoke()); //check
        registerClanCommand(new CommandKick()); //check

        registerClanCommand(new CommandLeave()); //check

        registerClanCommand(new CommandCreate()); //check
        registerClanCommand(new CommandJoin()); //check
        registerClanCommand(new CommandInvitations()); //check
        registerClanCommand(new CommandRanking());
        registerClanCommand(new CommandInfo()); //check
        registerClanCommand(new CommandStats()); //check



        plugin.getServer().getPluginManager().registerEvents(new InventoryEdit(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeath(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);

    }


    private void registerClanCommand(ClanCommand clanCommand) {
        CommandManager.registerCommand(clanCommand);
    }

    public static Config getClansFile() {
        return clans;
    }

    public static Config getClanUserFile() {
        return clanUserFile;
    }

    public static Config getInvitationFile() {
        return clanInvitations;
    }

    public static int isValidTag(String tag) {
        if (!(tag.length() >= 3 && tag.length() <= 5)) {
            return 1;
        }
        for (String c : tag.split("")) {
            if (!allowedTagChars.contains(c)) {
                return 2;
            }
        }
        return 0;
    }

    public static int isValidName(String name) {
        if (!(name.length() >= 3 && name.length() <= 16)) {
            return 1;
        }
        for (String c : name.split("")) {
            if (!allowedNameChars.contains(c)) {
                return 2;
            }
        }
        return 0;
    }

}
