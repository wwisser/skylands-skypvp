package me.skylands.skypvp.stats.context.impl.internal;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserService;

import java.util.Map;

public class KillToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.KILLS;

    private UserService service;

    public KillToplistContext(UserService service) { this.service = service; }

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        return this.service.getUserRepository().fetchByColumn(STATS_LABEL.getDatabaseColumn());
    }

}
