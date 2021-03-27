package me.skylands.skypvp.clan.util.clan;

import me.skylands.skypvp.clan.Clans;
import me.skylands.skypvp.clan.ModuleUUID;
import me.skylands.skypvp.config.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Clan {

    private String clanTag;
    private String clanTagCase;
    private String clanName;
    private Long created;
    private ClanAccess clanAccess;
    private int level;
    private ClanUser owner;
    public ArrayList<ClanUser> clanMember;
    private Config clansFile = Clans.getClansFile();
    private Config userFile = Clans.getClanUserFile();

    private int kills;
    private int deaths;

    private Clan(String clanTag) {
        this.clanTag = clanTag.toUpperCase();
        this.clanTagCase = this.clansFile.getString(this.clanTag + ".tag");
        this.clanName = this.clansFile.getString(this.clanTag + ".name");
        this.created = this.clansFile.getLong(this.clanTag + ".created");
        this.clanAccess = ClanAccess.valueOf(this.clansFile.getString(this.clanTag + ".access"));
        this.level = this.clansFile.getInt(this.clanTag + ".level");
        this.clanMember = new ArrayList<>();
        String ownerUuid = this.clansFile.getString(this.clanTag + ".owner");
        for (String userData : this.clansFile.getStringList(this.clanTag + ".member")) {
            ClanUser clanUser = new ClanUser(this.clanTag, userData);
            this.clanMember.add(clanUser);
            if (clanUser.getUuid().equals(ownerUuid)) {
                this.owner = clanUser;
            }
        }
    }

    private Clan(String clanTag, String clanName, ClanUser owner) {
        this.clanTag = clanTag.toUpperCase();
        this.clanTagCase = clanTag;
        this.clanName = clanName;
        this.created = System.currentTimeMillis();
        this.clanAccess = ClanAccess.INVITE_ONLY;
        this.owner = owner;
        this.clanMember = new ArrayList<>(Arrays.asList(this.owner));
    }

    public static Clan getClanByTag(String clanTag) {
        if (Clans.getClansFile().contains(clanTag)) {
            return new Clan(clanTag);
        } else {
            return null;
        }
    }

    public static Clan createClan(String clanTag, String clanName, Player owner) {
        Clan clan = new Clan(clanTag, clanName, new ClanUser(clanTag, StringUtils.join(new String[]{
                owner.getName(),
                owner.getUniqueId().toString(),
                String.valueOf(System.currentTimeMillis()),
                ClanRank.OWNER.toString(),
                String.valueOf(0),
                String.valueOf(0)
        }, ";")));
        clan.saveToFile(true);
        return clan;
    }

    public void addMember(String uuid) {
        this.clanMember.add(new ClanUser(this.clanTag, StringUtils.join(new String[]{
                ModuleUUID.getNameByUuid(uuid),
                uuid,
                String.valueOf(System.currentTimeMillis()),
                ClanRank.MEMBER.toString(),
                String.valueOf(0),
                String.valueOf(0)
        }, ";")));
        this.userFile.set(uuid, this.clanTag);
        this.userFile.saveFile();
        this.saveToFile(true);
    }

    public void removeMember(String uuid) {
        ArrayList<ClanUser> clanUsers = new ArrayList<>();

        for (ClanUser clanUser : this.clanMember) {
            if (!clanUser.getUuid().equals(uuid)) {
                clanUsers.add(clanUser);
            }
        }
        this.userFile.set(uuid, null);
        this.userFile.saveFile();
        this.clanMember = clanUsers;
        this.saveToFile(true);
    }

    public boolean isMember(String uuid) {
        for (ClanUser clanUser : this.clanMember) {
            if (clanUser.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void setAccess(ClanAccess access) {
        this.clanAccess = access;
        this.saveToFile(true);
    }

    public void delete() {
        for (ClanUser clanUser : this.clanMember) {
            this.userFile.set(clanUser.getUuid(), null);
        }
        this.userFile.saveFile();
        this.clansFile.set(this.clanTag, null);
        this.clansFile.saveFile();
    }

    public String getTag() {
        return this.clanTag;
    }

    public String getTagCase() {
        return this.clanTagCase;
    }

    public String getName() {
        return this.clanName;
    }

    public Long getCreated() {
        return this.created;
    }

    public String getCreatedFormatted() {
        return new SimpleDateFormat("d.M.Y H:m:s 'Uhr'").format(new Date(this.created));
    }

    public ClanAccess getClanAccess() {
        return this.clanAccess;
    }

    public ClanUser getOwner() {
        return this.owner;
    }

    public ArrayList<ClanUser> getMember() {
        return this.clanMember;
    }

    public ArrayList<ClanUser> getMember(ClanRank clanRank) {
        ArrayList<ClanUser> members = new ArrayList<>();
        for (ClanUser clanUser : this.clanMember) {
            if (clanUser.getRank().equals(clanRank)) {
                members.add(clanUser);
            }
        }
        return members;
    }

    public void updateStats() {
        for (ClanUser clanUser : this.clanMember) {
            this.kills += clanUser.getKills();
            this.deaths += clanUser.getDeaths();
        }
    }

    public int getClanKills() {
        return this.kills;
    }

    public int getClanDeaths() {
        return this.deaths;
    }

    public double getClanKDr() {
        double kd = 0;
        if (this.deaths == 0) {
            kd = this.kills;
        } else {
            kd = (double) this.kills / (double) this.deaths;
            String kd_str = String.valueOf(kd);
            if (kd_str.length() > 4) {
                kd_str = kd_str.substring(0, 4);
                kd = Double.parseDouble(kd_str);
            }
        }
        return kd;
    }

    public void broadcastMessage(String message) {
        for (ClanUser clanUser : this.clanMember) {
            Player player = Bukkit.getPlayer(clanUser.getName());

            if (player != null && player.isOnline()) {
                player.sendMessage(message);
            }
        }
    }

    public void saveToFile(boolean saveFile) {
        this.clansFile.set(this.clanTag + ".tag", this.clanTagCase);
        this.clansFile.set(this.clanTag + ".name", this.clanName);
        this.clansFile.set(this.clanTag + ".created", this.created);
        this.clansFile.set(this.clanTag + ".access", this.clanAccess.toString());
        this.clansFile.set(this.clanTag + ".level", this.level);
        this.clansFile.set(this.clanTag + ".owner", this.owner.getUuid());
        ArrayList<String> clanMember = new ArrayList<>();
        for (ClanUser clanUser : this.clanMember) {
            clanMember.add(clanUser.toString());
        }
        this.clansFile.set(this.clanTag + ".member", clanMember);
        if (saveFile) {
            this.clansFile.saveFile();
        }
    }

}
