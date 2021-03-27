package me.skylands.skypvp.clan.commands.moderation;

import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanRank;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@CommandData(name = "disband", description = "Löse deinen Clan auf", permission = ClanRank.OWNER)
public class CommandDisband extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        Clan clan = clanUser.getClan();
        if (args.length == 0) {
            ((CraftPlayer) p).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(
                            Message.DISBAND_CONFIRM)));
        } else {
            if (args[0].equalsIgnoreCase("confirm")) {
                clan.broadcastMessage(Message.DISBAND_CONFIRM_BROADCAST);
                clan.delete();
            } else if (args[0].equalsIgnoreCase("no-confirm")) {
                clan.broadcastMessage(Message.DISBAND_CONFIRM_DECLINE);
            }
        }
    }

}