package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandData(name = "stats", args = "<spieler>", description = "Statistiken")
public class CommandStats extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            String name = args[0];
            Player target = Bukkit.getPlayer(name);
            String uuid = (target != null && target.isOnline()) ? target.getUniqueId().toString()
                    : ModuleUUID.getUuidByName(name);

            if (uuid != null) {
                if (ClanManager.hasClan(uuid)) {
                    ClanUser clanTarget = ClanManager.getClanUserByUuid(uuid);
                    Clan clan = clanTarget.getClan();

                    p.sendMessage(Message.format(Message.STATS_COMMAND_HAS_CLAN,
                            ((target != null && target.isOnline()) ? "§a" : "§c") + name,
                            clan.getName(),
                            clan.getTagCase(),
                            clanTarget.getRank().getColor() + clanTarget.getRank().getFullName(),
                            clanTarget.getJoinedFormatted(),
                            String.valueOf(clanTarget.getKills()),
                            String.valueOf(clanTarget.getDeaths()),
                            String.valueOf(clanTarget.getKDr())));
                } else {
                    p.sendMessage(Message.STATS_COMMAND_NO_CLAN);
                }
            } else {
                p.sendMessage(Message.STATS_NOT_FOUND);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}