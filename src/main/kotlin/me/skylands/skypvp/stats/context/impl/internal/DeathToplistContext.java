package me.skylands.skypvp.stats.context.impl.internal;

import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserRepository;

import java.util.Map;

public class DeathToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.DEATHS;

    private UserRepository userRepository;

    public DeathToplistContext(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        return this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
    }

}
