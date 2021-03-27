package me.skylands.skypvp.clan.util.clan;

public enum ClanAccess {

    CLOSED("§cNiemand"),
    INVITE_ONLY("§eNur auf Einladung"),
    OPEN("§aJeder");

    private String name;

    ClanAccess(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
