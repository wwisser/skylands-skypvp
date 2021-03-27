package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.ClanInvitation;
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

@CommandData(name = "invite", args = "<spieler>", description = "Verschicke Einladungen", permission = ClanRank.MODERATOR)
public class CommandInvite extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null && target.isOnline()) {
                if (!ClanManager.hasClan(target)) {
                    String clanTag = ClanManager.getClanTag(p);
                    if (!ClanManager.hasInvitation(target, clanTag)) {
                        ClanManager.addInvitation(target,
                                new ClanInvitation(clanTag, p.getUniqueId().toString()));
                        p.sendMessage(Message.format(Message.INVITE_INVITED, target.getName()));
                        target.sendMessage(Message.format(Message.INVITE_RECIEVED,
                                clanUser.getRank().getColor() + p.getName(),
                                clanUser.getClan().getName(), clanTag));
                        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(
                                new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                                        .a(Message.format(Message.INVITE_ACCEPT, clanTag))));
                    } else {
                        p.sendMessage(Message.INVITE_ALREADY_INVITED);
                    }
                } else {
                    p.sendMessage(Message.INVITE_ALREADY_CLAN);
                }
            } else {
                p.sendMessage(Message.INVITE_NOT_ONLINE);
            }
        } else {
            sendCommandHelp(p);
        }
    }

}