package me.skylands.skypvp.stats.context.impl.internal;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class PlaytimeToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.PLAYTIME;

    private UserRepository userRepository;

    public PlaytimeToplistContext(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        Map<String, ? super Number> fetched = this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
        Map<String, ? super Number> converted = new HashMap<>();

        fetched.forEach((name, minutes) -> converted.put(name, ((Number) minutes).longValue() / 60));

        return converted;
    }

}
