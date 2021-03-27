package me.skylands.skypvp.clan.util.clan;

import me.skylands.skypvp.clan.Clans;
import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.config.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClanManager {

    private static Config clansFile = Clans.getClansFile();
    private static Config userFile = Clans.getClanUserFile();
    private static Config invitationFile = Clans.getInvitationFile();

    public static boolean hasClan(String uuid) {
        return userFile.contains(uuid);
    }

    public static boolean hasClan(Player player) {
        return hasClan(player.getUniqueId().toString());
    }

    public static boolean existClan(String clanTag) {
        return clansFile.contains(clanTag.toUpperCase());
    }

    public static Clan createClan(String clanTag, String clanName, Player owner) {
        userFile.set(owner.getUniqueId().toString(), clanTag.toUpperCase());
        userFile.saveFile();
        return Clan.createClan(clanTag, clanName, owner);
    }

    public static Clan getClanByTag(String clanTag) {
        return Clan.getClanByTag(clanTag.toUpperCase());
    }

    public static ClanUser getClanUserByUuid(String uuid) {
        ClanUser clanUser = null;

        if (hasClan(uuid)) {
            for (ClanUser user : getClanByTag(userFile.getString(uuid)).getMember()) {
                if (user.getUuid().equals(uuid)) {
                    clanUser = user;
                }
            }
        }
        return clanUser;
    }

    public static ClanUser getClanUserByName(String name) {
        return getClanUserByUuid(ModuleUUID.getUuidByName(name));
    }

    public static ClanUser getClanUserByPlayer(Player player) {
        return getClanUserByUuid(player.getUniqueId().toString());
    }

    public static String getClanTag(String uuid) {
        return clansFile.getString(userFile.getString(uuid) + ".tag");
    }

    public static String getClanTag(Player player) {
        return getClanTag(player.getUniqueId().toString());
    }

    public static ClanInvitation[] getInvitations(String uuid) {
        ArrayList<ClanInvitation> invitations = new ArrayList<>();
        if (invitationFile.contains(uuid)) {
            for (String data : invitationFile.getStringList(uuid)) {
                invitations.add(new ClanInvitation(data));
            }
        }
        return invitations.toArray(new ClanInvitation[invitations.size()]);
    }

    public static ClanInvitation[] getInvitations(Player player) {
        return getInvitations(player.getUniqueId().toString());
    }

    public static boolean hasInvitation(String uuid, String tag) {
        ClanInvitation[] invitations = getInvitations(uuid);
        if (invitations.length > 0) {
            for (ClanInvitation clanInvitation : getInvitations(uuid)) {
                if (clanInvitation.getClanTag().equals(tag.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasInvitation(Player player, String tag) {
        return hasInvitation(player.getUniqueId().toString(), tag);
    }

    public static void addInvitation(String uuid, ClanInvitation invitation) {
        ArrayList<ClanInvitation> invitations = new ArrayList<>();
        if (invitationFile.contains(uuid)) {
            for (String data : invitationFile.getStringList(uuid)) {
                invitations.add(new ClanInvitation(data));
            }
        }
        invitations.add(invitation);
        ArrayList<String> add = new ArrayList<>();
        for (ClanInvitation clanInvitation : invitations) {
            add.add(clanInvitation.toString());
        }
        invitationFile.set(uuid, add);
        invitationFile.saveFile();
    }

    public static void addInvitation(Player player, ClanInvitation invitation) {
        addInvitation(player.getUniqueId().toString(), invitation);
    }

    public static void removeInvitation(String uuid, String tag) {
        ArrayList<String> invitationsData = new ArrayList<>();
        if (invitationFile.contains(uuid)) {
            for (String data : invitationFile.getStringList(uuid)) {
                if (!data.startsWith(tag.toUpperCase() + ";")) {
                    invitationsData.add(data);
                }
            }
            invitationFile.set(uuid, invitationsData);
            invitationFile.saveFile();
        }
    }

    public static void removeInvitation(Player player, String tag) {
        removeInvitation(player.getUniqueId().toString(), tag);
    }

    public static void clearInvitations(String uuid) {
        if (invitationFile.contains(uuid)) {
            invitationFile.set(uuid, null);
            invitationFile.saveFile();
        }
    }

    public static void clearInvitations(Player player) {
        clearInvitations(player.getUniqueId().toString());
    }

    public static String getClanColor(Clan clan) {
        String clancolor = "§f";
        int kills = clan.getClanKills();
        if (kills < 200) {
            clancolor = "§7";
        }
        if ((kills >= 200) && (kills <= 500)) {
            clancolor = "§a";
        }
        if ((kills >= 500) && (kills <= 1000)) {
            clancolor = "§2";
        }
        if ((kills >= 1000) && (kills <= 1500)) {
            clancolor = "§c";
        }
        if ((kills >= 1500) && (kills <= 2000)) {
            clancolor = "§4";
        }
        if ((kills >= 2000) && (kills <= 3000)) {
            clancolor = "§b";
        }
        if (kills >= 3000) {
            clancolor = "§6§l";
        }
        return clancolor;
    }

}
