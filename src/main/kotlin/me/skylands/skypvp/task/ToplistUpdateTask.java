package me.skylands.skypvp.task;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ToplistUpdateTask extends BukkitRunnable {

    private static final int TOP_LIST_SIZE = 10;
    private static ConcurrentHashMap<StatsLabel, LinkedHashMap<String, ? super Number>> topLists = new ConcurrentHashMap<>();

    private static final int POSITION_X_START = 45;
    private static final int HEAD_AMOUNT = 5;
    private static final Location LABEL_LOCATION = new Location(SkyLands.WORLD_SKYPVP, 47, 125, 134);

    private final ToplistContext[] toplistContexts;
    private int currentIndex = 0;

    public ToplistUpdateTask(ToplistContext[] toplistContexts) {
        this.toplistContexts = toplistContexts;
    }

    @Override
    public void run() {
        ToplistContext toplistContext = this.toplistContexts[this.currentIndex];

        StatsLabel statsLabel = toplistContext.getLabel();
        Sign labelSign = (Sign) LABEL_LOCATION.getBlock().getState();

        labelSign.setLine(2, "§l" + statsLabel.getDisplayName());
        labelSign.update();

        LinkedHashMap<String, ? super Number> topList = this.sort(toplistContext.getData());
        Location currentHead = new Location(SkyLands.WORLD_SKYPVP, POSITION_X_START, 124, 134);
        int count = 0;

        topLists.put(statsLabel, topList);
        Iterator<? extends Map.Entry<String, ? super Number>> iterator = topList.entrySet().iterator();
        Map.Entry<String, ? super Number> entry = iterator.next();

        for (int i = 0; i < 5; i++) {

            Skull skull = (Skull) currentHead.getBlock().getState();

            skull.setOwner(entry.getKey());
            skull.update();

            Location lowerLoc = currentHead.clone().add(0, -1, 0);

            Sign sign = (Sign) lowerLoc.getBlock().getState();

            sign.setLine(0, "§l#" + (count + 1));
            sign.setLine(1, entry.getKey());
            sign.setLine(2, String.valueOf(entry.getValue()));
            sign.setLine(3, statsLabel.getAdditive());
            sign.update();

            currentHead.add(1, 0, 0);
            count++;
            entry = iterator.next();
        }

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
