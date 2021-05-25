package me.skylands.skypvp.stats.context.impl.external;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import lombok.AllArgsConstructor;
import me.skylands.skypvp.stats.context.ToplistContext;
import me.skylands.skypvp.stats.label.StatsLabel;
import me.skylands.skypvp.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class IslandLevelToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.ISLAND_LEVEL;

    private UserRepository userRepository;
    private ASkyBlockAPI aSkyBlockApi;

    public IslandLevelToplistContext(UserRepository userRepository, ASkyBlockAPI aSkyBlockApi) {
        this.userRepository = userRepository; this.aSkyBlockApi = aSkyBlockApi;
    }

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        Map<String, ? super Number> result = new HashMap<>();

        this.aSkyBlockApi.getLongTopTen().forEach((uuid, level) -> {
            String name = this.userRepository.fetchNameByUuid(uuid.toString());
            result.put(name, level);
        });

        return result;
    }

}
