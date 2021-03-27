package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@CommandData(name = "info", args = "<clan-tag>", description = "Clan-Informationen")
public class CommandInfo extends ClanCommand {

    @Override
    public void run(final Player p, ClanUser cU, String[] args) {
        if (args.length == 1) {
            final String tag = args[0];

            if (ClanManager.existClan(tag)) {
                p.sendMessage(Message.INFO_LOADING);
                Bukkit.getScheduler().runTaskAsynchronously(SkyLands.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Clan clan = ClanManager.getClanByTag(tag);
                        clan.updateStats();
                        p.sendMessage(Message.format(Message.INFO_COMMAND, clan.getName(),
                                clan.getTagCase(), clan.getOwner().getName(),
                                clan.getCreatedFormatted(), clan.getClanAccess().getName(),
                                String.valueOf(clan.getClanKills()),
                                String.valueOf(clan.getClanDeaths()),
                                String.valueOf(clan.getClanKDr()),
                                String.valueOf(clan.getMember().size())));
                        for (ClanRank clanRank : new ClanRank[]{ClanRank.OWNER, ClanRank.ADMIN,
                                ClanRank.MODERATOR, ClanRank.MEMBER}) {
                            for (ClanUser clanUser : clan.getMember(clanRank)) {
                                Player player = Bukkit.getPlayer(clanUser.getName());
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(
                                        new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                                                .a(Message.format(Message.INFO_LINE,
                                                        (player != null && player.isOnline()) ? "§a"
                                                                : "§c",
                                                        clanUser.getName(),
                                                        clanUser.getRank().getColor() + clanUser
                                                                .getRank().getFullName(),
                                                        String.valueOf(clanUser.getKills()),
                                                        String.valueOf(clanUser.getDeaths()),
                                                        String.valueOf(clanUser.getKDr())))));
                            }
                        }
                    }
                });
            } else {
                p.sendMessage(Message.INFO_NOT_FOUND);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}