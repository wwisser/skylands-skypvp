package me.skylands.skypvp.clan.util.clan;

public enum ClanRank {

    OWNER(4, 500, "Besitzer", "Besitzer", "§4"),
    ADMIN(3, 100, "Admin", "Admin", "§c"),
    MODERATOR(2, 50, "Mod", "Moderator", "§9"),
    MEMBER(1, 10, "Mitglied", "Mitglied", "§2"),
    NO_CLAN(0, 0, "Clanlos", "Clanlos", "§8");

    private int id;
    private int permissionLevel;
    private String shortName;
    private String fullName;
    private String color;

    ClanRank(int id, int permissionLevel, String shortName, String fullName, String color) {
        this.id = id;
        this.permissionLevel = permissionLevel;
        this.shortName = shortName;
        this.fullName = fullName;
        this.color = color;
    }

    public int getId() {
        return this.id;
    }

    public int getPermissionLevel() {
        return this.permissionLevel;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getColor() {
        return this.color;
    }

    public static ClanRank getRankById(int id) {
        for (ClanRank clanRank : ClanRank.values()) {
            if (clanRank.getId() == id) {
                return clanRank;
            }
        }
        return ClanRank.MEMBER;
    }

}
