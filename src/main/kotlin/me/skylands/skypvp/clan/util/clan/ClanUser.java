package me.skylands.skypvp.clan.util.clan;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClanUser {

    private String name;
    private String uuid;
    private String tag;
    private Long joined;
    private ClanRank rank;
    private int kills;
    private int deaths;

    public ClanUser(String tag, String userData) {
        String[] data = userData.split(";");

        this.tag = tag.toUpperCase();
        this.name = data[0];
        this.uuid = data[1];
        this.joined = Long.parseLong(data[2]);
        this.rank = ClanRank.valueOf(data[3]);
        this.kills = Integer.parseInt(data[4]);
        this.deaths = Integer.parseInt(data[5]);
    }

    public void setName(String name) {
        this.name = name;
        this.update();
    }

    public void setRank(ClanRank clanRank) {
        this.rank = clanRank;
        this.update();
    }

    public void setKills(int kills) {
        this.kills = kills;
        this.update();
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        this.update();
    }

    public void addKill() {
        this.kills++;
        this.update();
    }

    public void addDeath() {
        this.deaths++;
        this.update();
    }

    public String getName() {
        return this.name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Clan getClan() {
        return ClanManager.getClanByTag(this.tag);
    }

    public Long getJoined() {
        return this.joined;
    }

    public String getJoinedFormatted() {
        return new SimpleDateFormat("d.M.Y H:m:s 'Uhr'").format(new Date(this.joined));
    }

    public ClanRank getRank() {
        return this.rank;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public double getKDr() {
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

    public String toString() {
        return StringUtils.join(new String[]{this.name, this.uuid, String.valueOf(this.joined),
                        this.rank.toString(), String.valueOf(this.kills), String.valueOf(this.deaths)},
                ";");
    }

    private void update() {
        Clan clan = this.getClan();
        for (int i = 0; i < clan.clanMember.size(); i++) {
            if (clan.clanMember.get(i).getUuid().equals(this.uuid)) {
                clan.clanMember.set(i, new ClanUser(this.tag, this.toString()));
                clan.saveToFile(true);
            }
        }
    }

}
