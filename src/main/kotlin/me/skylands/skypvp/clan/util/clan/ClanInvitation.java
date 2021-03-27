package me.skylands.skypvp.clan.util.clan;

import org.apache.commons.lang.StringUtils;

public class ClanInvitation {

    private String clanTag;
    private String invitedFrom;

    public ClanInvitation(String clanTag, String invitedFrom) {
        this.clanTag = clanTag.toUpperCase();
        this.invitedFrom = invitedFrom;
    }

    public ClanInvitation(String invitationData) {
        String[] data = invitationData.split(";");

        this.clanTag = data[0].toUpperCase();
        this.invitedFrom = data[1];
    }

    public String getClanTag() {
        return this.clanTag;
    }

    public String getInvitedFrom() {
        return this.invitedFrom;
    }

    public String toString() {
        return StringUtils.join(new String[]{this.clanTag, this.invitedFrom}, ";");
    }

}
