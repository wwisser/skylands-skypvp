package me.skylands.skypvp.task;

import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ToplistUpdateTask extends BukkitRunnable {

    private static final int TOP_LIST_SIZE = 10;
    private static ConcurrentHashMap<StatsLabel, LinkedHashMap<String, ? super Number>> topLists = new ConcurrentHashMap<>();

    private final ToplistContext[] toplistContexts;
    private int currentIndex = 0;

    public ToplistUpdateTask(ToplistContext[] toplistContexts) {
        this.toplistContexts = toplistContexts;
    }

    @Override
    public void run() {
        ToplistContext toplistContext = this.toplistContexts[this.currentIndex];

        StatsLabel statsLabel = toplistContext.getLabel();
        LinkedHashMap<String, ? super Number> topList = this.sort(toplistContext.getData());

        topLists.put(statsLabel, topList);

        if (this.currentIndex >= (this.toplistContexts.length - 1)) {
            this.currentIndex = 0;
        } else {
            this.currentIndex++;
        }
    }

    private LinkedHashMap<String, ? super Number> sort(Map<String, ? super Number> data) {
        LinkedHashMap<String, ? super Number> topList = new LinkedHashMap<>();

        for (int i = 0; i < TOP_LIST_SIZE; i++) {
            Number maxValue = 0;
            String maxValueKey = "";

            for (Map.Entry<String, ? super Number> entry : data.entrySet()) {
                if (((Number) entry.getValue()).longValue() > maxValue.longValue()) {
                    maxValue = (Number) entry.getValue();
                    maxValueKey = entry.getKey();
                }
            }

            data.remove(maxValueKey);
            topList.put(maxValueKey, maxValue);
        }

        return topList;
    }

    public static LinkedHashMap<String, ? super Number> getTopListByLabel(final StatsLabel label) {
        return topLists.getOrDefault(label, new LinkedHashMap<>());
    }

}
