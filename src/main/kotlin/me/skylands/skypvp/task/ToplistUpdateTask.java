package me.skylands.skypvp.task;

import lombok.RequiredArgsConstructor;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Deprecated
public class ToplistUpdateTask extends BukkitRunnable {

    private static final long DELAY = 20L * 10;
    private static final long PERIOD = 20L * 60 * 2;

    private static final int POSITION_Z_START = -355;
    private static final int HEAD_AMOUNT = 5;
    private static final Location LABEL_LOCATION = new Location(SkyLands.WORLD_SKYPVP, -47, 186, -353);

    private final ToplistContext[] toplistContexts;
    private int currentIndex = 0;


    @Override
    public void run() {
        ToplistContext toplistContext = this.toplistContexts[this.currentIndex];

        StatsLabel statsLabel = toplistContext.getLabel();
        Sign labelSign = (Sign) LABEL_LOCATION.getBlock().getState();

        labelSign.setLine(2, "§l" + statsLabel.getDisplayName());
        labelSign.update();

        Map<String, ? super Number> topList = this.sort(toplistContext.getData());
        Location currentHead = new Location(SkyLands.WORLD_SKYPVP, -46, 185, POSITION_Z_START);
        int count = 0;

        for (Map.Entry<String, ? super Number> entry : topList.entrySet()) {
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

            currentHead.add(0, 0, 1);
            count++;
        }

        if (this.currentIndex >= (this.toplistContexts.length - 1)) {
            this.currentIndex = 0;
        } else {
            this.currentIndex++;
        }
    }

    private Map<String, ? super Number> sort(Map<String, ? super Number> data) {
        Map<String, ? super Number> topList = new LinkedHashMap<>();

        for (int i = 0; i < HEAD_AMOUNT; i++) {
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

}