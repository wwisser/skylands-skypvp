package me.skylands.skypvp.stats.context.impl.internal;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserRepository;

import java.util.Map;

@AllArgsConstructor
public class VoteToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.VOTES;

    private UserRepository userRepository;

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        return this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
    }

}