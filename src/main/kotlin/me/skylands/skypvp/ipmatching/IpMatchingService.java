package me.skylands.skypvp.ipmatching;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.config.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IpMatchingService {

    private Config database = new Config(SkyLands.CONFIG_PATH, "ips.yml");

    public Set<String> getAlternativeIps(final String uuid) {
        return this.getEntries("ips." + uuid);
    }

    /**
     * @return one of the UUIDs could be the same as given IP owner
     */
    public Set<String> getAlternativeAccounts(final String ip) {
        return this.getEntries("players." + ip);
    }

    public Set<String> getEntries(final String path) {
        Set<String> entries;

        if (this.database.contains(path)) {
            entries = new HashSet<>(this.database.getStringList(path));
        } else {
            entries = new HashSet<>();
        }

        return entries;
    }

    public void updateEntries(final String path, final Set<String> entries) {
        this.database.set(path, new ArrayList<>(entries));
        this.database.saveFile();
    }

}
