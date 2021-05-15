package me.skylands.skypvp.stats.label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatsLabel {

    KILLS("Kills", "Kills", 2),
    KILLSTREAK("Killstreak", "Killstreak", 3),
    BLOODPOINTS("Blutpunkte", "Blutpunkte", 4),
    DEATHS("Tode", "Tode", 5),
    VOTES("Votes", "Votes", 6),
    PLAYTIME("Spielzeit", "Stunden", 7),
    LEVEL("Level", "Level", 8),
    ISLAND_LEVEL("IS-Level", "Level", -1);

    private String displayName;
    private String additive;
    private int databaseColumn;

}
