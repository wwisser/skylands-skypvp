package me.skylands.skypvp.clan.util.command;

import me.skylands.skypvp.clan.util.clan.ClanUser;
import org.bukkit.entity.Player;

public abstract class ClanCommand {

    public abstract void run(Player p, ClanUser clanUser, String[] args);

    public void sendCommandHelp(Player p) {
        p.performCommand("clan");
    }

}
