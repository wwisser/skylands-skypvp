package me.skylands.skypvp.stats.context.impl.internal;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserRepository;

import java.util.Map;

public class JewelToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.LEVEL;

    private UserRepository userRepository;

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    public JewelToplistContext(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public Map<String, ? super Number> getData() {
        return this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
    }

}
