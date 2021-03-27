package me.skylands.skypvp.stats.label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatsLabel {

    KILLS("Kills", "Kills", 2),
    DEATHS("Tode", "Tode", 3),
    VOTES("Votes", "Votes", 4),
    PLAYTIME("Spielzeit", "Stunden", 5),
    LEVEL("Level", "Level", 6),
    ISLAND_LEVEL("IS-Level", "Level", -1);

    private String displayName;
    private String additive;
    private int databaseColumn;

}
