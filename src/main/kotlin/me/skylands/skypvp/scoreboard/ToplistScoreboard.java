package me.skylands.skypvp.scoreboard;

import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.task.ToplistUpdateTask;

import java.util.Map;

public class ToplistScoreboard extends DynamicScoreboard {

    public ToplistScoreboard() {
        super("§6§lTop Inseln");
        for (Map.Entry<String, ? super Number> entry : ToplistUpdateTask.getTopListByLabel(StatsLabel.ISLAND_LEVEL).entrySet()) {
            if (entry.getKey().isEmpty()) {
                continue;
            }

            super.addStaticLine("§7" + entry.getKey(), ((Number) entry.getValue()).intValue());
        }
    }

}