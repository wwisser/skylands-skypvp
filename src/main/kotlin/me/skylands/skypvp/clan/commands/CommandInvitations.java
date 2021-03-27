package me.skylands.skypvp.clan.commands;

import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.clan.settings.Message;
import me.skylands.skypvp.clan.util.clan.Clan;
import me.skylands.skypvp.clan.util.clan.ClanInvitation;
import me.skylands.skypvp.clan.util.clan.ClanManager;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.clan.util.command.ClanCommand;
import me.skylands.skypvp.clan.util.command.CommandData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@CommandData(name = "invitations", description = "Einladungen", strictPermission = true)
public class CommandInvitations extends ClanCommand {

    @Override
    public void run(Player p, ClanUser clanUser, String[] args) {
        ClanInvitation[] invitations = ClanManager.getInvitations(p);

        if (invitations.length > 0) {
            p.sendMessage(Message.INVITATIONS_PREFIX);
            for (ClanInvitation invitation : invitations) {
                if (ClanManager.existClan(invitation.getClanTag())) {
                    Clan clan = ClanManager.getClanByTag(invitation.getClanTag());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(
                            IChatBaseComponent.ChatSerializer
                                    .a(Message.format(Message.INVITATIONS_LINE,
                                            clan.getName(),
                                            clan.getTagCase(),
                                            ModuleUUID.getNameByUuid(invitation.getInvitedFrom())))));
                } else {
                    ClanManager.removeInvitation(p, invitation.getClanTag());
                }
            }
        } else {
            p.sendMessage(Message.INVITATIONS_NONE);
        }
    }

}