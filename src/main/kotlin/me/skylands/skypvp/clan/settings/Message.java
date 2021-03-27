package me.skylands.skypvp.clan.settings;

import me.skylands.skypvp.Messages;

public class Message {

    public static String PREFIX = Messages.PREFIX;
    public static String PREFIX_LONG = "§7\n§7\n";
    //public static String PREFIX_LONG = "§7\n§7\n[§8§m---------------§7[ §e§lClan §7]§8§m---------------§7]";

    public static String NO_PERMISSION = "§cDu hast keinen Zugriff auf diesen Befehl.";
    public static String NOT_PLAYER = "Dieser Command ist nur für Spieler!";
    public static String ERROR = "§cFehler: §c%1";
    public static String USAGE = "§cVerwende: %1";

    public static String CLAN_CHAT = "§e§lCLANCHAT §e» §e%1:§7 %2";
    public static String FRIENDLYFIRE = PREFIX + "§cDu kannst keine Clankameraden angreifen.";

    public static String NO_CLAN_PERMISSION =
            PREFIX + "Für diesen Command benötigst du den Clan-Rang %1§7.";

    public static String DEFAULT_COMMAND_LINE = PREFIX + "§e/clan %1 %2 §8- §7 %3";
    public static String DEFAULT_TAGINFO =
            PREFIX + "§oClan-Tag (max. 5 Zeichen): z.B. SLT\n" + PREFIX
                    + "§oClan-Name (max. 16 Zeichen): §oz.B. SkyLands-Team";

    public static String CREATE_ALREADY_TAKEN = PREFIX + "§cDieser Clan existiert bereits!";
    public static String CREATE_INVALID_NAME = PREFIX + "§cDer %1 enthält ein ungültiges Zeichen.";
    public static String CREATE_INVALID_LENGTH =
            PREFIX + "§cDer %1 muss zwischen %2 und %3 Zeichen lang sein.";
    public static String CREATE_SUCCESSFUL =
            PREFIX + "Du hast den Clan §e%1 §7mit dem Clan-Tag §e%2 §7erfolgreich erstellt.";

    public static String DEMOTE_NOT_FOUND = PREFIX + "§cDer Spieler wurde nicht gefunden.";
    public static String DEMOTE_NOT_CLAN = PREFIX + "§cDieser Spieler ist nicht in deinem Clan.";
    public static String DEMOTE_MAX =
            PREFIX + "§cDu kannst diesen Spieler nicht weiter degradieren.";
    public static String DEMOTE_BROADCAST =
            PREFIX + "Der Spieler §e%1 §7wurde von %2 §7zum %3 §7degradiert.";

    public static String DISBAND_CONFIRM = "[\"\",{\"text\":\"> \",\"color\":\"dark_gray\"},{\"text\":\"Willst du deinen Clan wirklich auflösen? \",\"color\":\"gold\"},{\"text\":\"[JA]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan disband confirm\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§cDie Löschung kann nicht rückgängig gemacht werden\"}]}}},{\"text\":\" [NEIN] \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan disband no-confirm\"}}]";
    public static String DISBAND_CONFIRM_DECLINE =
            PREFIX + "§aDu hast deinen Clan nicht aufgelöst.";
    public static String DISBAND_CONFIRM_BROADCAST = PREFIX + "§cDer Clan wurde aufgelöst!";

    public static String EDIT_CHANGED_ACCESS = PREFIX + "Du hast den Clan-Beitritt geändert: %1";

    public static String INVITE_NOT_ONLINE = PREFIX + "§cDer Spieler ist nicht Online.";
    public static String INVITE_ALREADY_CLAN = PREFIX + "§cDer Spieler ist bereits in einem Clan.";
    public static String INVITE_ALREADY_INVITED =
            PREFIX + "§cDer Spieler wurde bereits in den Clan eingeladen.";
    public static String INVITE_INVITED =
            PREFIX + "Du hast den Spieler §e%1 §7in den Clan eingeladen.";
    public static String INVITE_RECIEVED =
            PREFIX + "Du wurdest von %1 §7in den Clan §e%2 §8[§e%3§8] §7eingeladen.";
    public static String INVITE_ACCEPT = "[\"\",{\"text\":\"> \",\"color\":\"dark_gray\"},{\"text\":\"Willst du dem Clan beitreten? \",\"color\":\"gray\"},{\"text\":\"[JA] \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan join %1\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aKlicke, um die Anfrage anzunehmen\"}]}}},{\"text\":\"[NEIN]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan join %1 decline\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§cKlicke, um die Anfrage abzulehnen\"}]}}}]";
    public static String INVITE_DECLINE = PREFIX + "Du hast die Einladung abgelehnt.";

    public static String KICK_NOT_FOUND = PREFIX + "§cDer Spieler wurde nicht gefunden.";
    public static String KICK_NOT_CLAN = PREFIX + "§cDieser Spieler ist nicht in deinem Clan.";
    public static String KICK_NOT_ALLOWED =
            PREFIX + "§cDu kannst diesen Spieler nicht aus dem Clan werfen.";
    public static String KICK_KICKED = PREFIX + "§cDu wurdest aus dem Clan geworfen.";
    public static String KICK_BROADCAST =
            PREFIX + "Der Spieler §e%1 §7wurde von %2 §7aus dem Clan geworfen.";

    public static String PROMOTE_NOT_FOUND = PREFIX + "§cDer Spieler wurde nicht gefunden.";
    public static String PROMOTE_NOT_CLAN = PREFIX + "§cDieser Spieler ist nicht in deinem Clan.";
    public static String PROMOTE_MAX =
            PREFIX + "§cDu kannst diesen Spieler nicht weiter befördern.";
    public static String PROMOTE_BROADCAST =
            PREFIX + "Der Spieler §e%1 §7wurde von %2 §7zum %3 §7befördert.";

    public static String REVOKE_NOT_FOUND = PREFIX + "§cDer Spieler wurde nicht gefunden.";
    public static String REVOKE_NO_INVITE = PREFIX + "§cDer Spieler hat keine Einladung erhalten.";
    public static String REVOKE_REVOKED =
            PREFIX + "Du hast die Einladung für §e%1 §7zurückgezogen.";

    public static String INFO_LOADING = PREFIX + "§cInformationen werden geladen...";
    public static String INFO_NOT_FOUND = PREFIX + "§cDer Clan wurde nicht gefunden.";
    public static String INFO_COMMAND = PREFIX_LONG + "\n"
            //clan, clan-tag, owner, created, access, kills, deaths, kdr, membercount
            + PREFIX + "Name: §e%1 §8[§e%2§8]\n"
            + PREFIX + "Besitzer: §e%3\n"
            + PREFIX + "Erstellt am §e%4\n"
            + PREFIX + "Beitritt: §e%5\n"
            + PREFIX + "Statistik: §e%6 §7Kills §8| §e%7 §7Deaths §8| §e%8 §7KDr\n"
            + PREFIX + "Mitglieder (§e%9§7): §8[§aOnline §7| §cOffline§8]";
    public static String INFO_LINE = "[\"\",{\"text\":\" - \",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\"%1%2\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" | \",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\"%3\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" | \",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\"%4\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" Kills\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" | \",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\"%5\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" Deaths\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" | \",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\"%6\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}},{\"text\":\" KDr\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %2\"}}]";
    //online, name, rank, kills, deaths, kdr

    public static String INVITATIONS_NONE = PREFIX + "§oDu hast momentan keine Einladungen.";
    public static String INVITATIONS_PREFIX = "§7\nDeine Clan Einladungen:";
    public static String INVITATIONS_LINE = "[\"\",{\"text\":\" - \",\"color\":\"gray\"},{\"text\":\"%1 \",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan info %2\"}},{\"text\":\"[\",\"color\":\"dark_gray\"},{\"text\":\"%2\",\"color\":\"gold\"},{\"text\":\"] \",\"color\":\"dark_gray\"},{\"text\":\"(von \",\"color\":\"gray\",\"italic\":true},{\"text\":\"%3\",\"color\":\"gold\",\"italic\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan stats %3\"}},{\"text\":\") \",\"color\":\"gray\",\"italic\":true},{\"text\":\"[BEITRETEN] \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan join %2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aKlicke, um die Anfrage anzunehmen\"}]}},\"italic\":false},{\"text\":\"[ABLEHNEN]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/clan join %2 decline\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§cKlicke, um die Anfrage abzulehnen\"}]}}}]";

    public static String JOIN_NOT_FOUND = PREFIX + "§cDer Clan wurde nicht gefunden.";
    public static String JOIN_ACCESS_PRIVATE =
            PREFIX + "§cDer Clan akzeptiert momentan keine neuen Spieler.";
    public static String JOIN_ACCESS_INVITE_ONLY =
            PREFIX + "§cDu musst erst in diesen Clan eingeladen werden.";
    public static String JOIN_JOINED = PREFIX + "Du bist dem Clan §e%1 §7beigetreten.";
    public static String JOIN_BROADCAST = PREFIX + "Der Spieler §e%1 §7ist dem Clan beigetreten.";

    public static String LEAVE_CANT = PREFIX + "§cDu kannst deinen Clan nicht verlassen.\n" + PREFIX
            + "§cWenn du ihn auflösen willst, verwende /clan disband";
    public static String LEAVE_LEFT = PREFIX + "Du hast den Clan §e%1 §7verlassen.";
    public static String LEAVE_BROADCAST = PREFIX + "Der Spieler §e%1 §7hat den Clan verlassen.";

    public static String STATS_NOT_FOUND = PREFIX + "§cDer Spieler wurde nicht gefunden.";
    public static String STATS_COMMAND_HAS_CLAN =
            PREFIX_LONG + "\n" //name, clan, clan-tag, clan-rang, joined, kills, deaths, kdr
                    + PREFIX + "Name: §e%1 §8[§aOnline §7| §cOffline§8]\n"
                    + PREFIX + "Clan: §e%2 §8[§e%3§8]\n"
                    + PREFIX + "Clan-Rang: %4\n"
                    + PREFIX + "Beigetreten am §e%5\n"
                    + PREFIX + "Statistik: §e%6 §7Kills §8| §e%7 §7Deaths §8| §e%8 §7KDr\n"
                    + PREFIX + "§o(Statistik seitdem er im Clan ist)";
    public static String STATS_COMMAND_NO_CLAN = PREFIX + "Dieser Spieler ist in keinem Clan.";

    public static String format(String message, String... arguments) {
        for (int i = 0; i < arguments.length; i++) {
            message = message.replace("%" + String.valueOf(i + 1), arguments[i]);
        }
        return message;
    }

}
