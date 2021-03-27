package me.skylands.skypvp.stats.context;

import me.skylands.skypvp.stats.label.StatsLabel;

import java.util.Map;

public interface ToplistContext {

    StatsLabel getLabel();

    Map<String, ? super Number> getData();

}
