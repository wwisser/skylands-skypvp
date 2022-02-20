package me.skylands.skypvp.pve;

import me.skylands.skypvp.pve.data.CacheData;

import java.util.HashMap;

public class BossCache {

    private HashMap<String, CacheData> cacheTable = new HashMap<String, CacheData>();
    private String lastHitBy = "";


    public boolean isRegistered(String player) {
        return cacheTable.containsKey(player);
    }

    public void register(String player) {
        if(!isRegistered(player)) {
            cacheTable.put(player, new CacheData(0.0, 0));
        }
    }

    public void setDamageDealt(String player, double newDamage) {
        if(!isRegistered(player)) {
            register(player);
            return;
        }

        if(!(lastHitBy.equals(""))) {
            if(lastHitBy.equals(player)) {
                setConsecutiveHits(player, getConsecutiveHits(player) + 1);
            } else {
                setConsecutiveHits(lastHitBy, 0);
                lastHitBy = player;
            }
        } else {
            lastHitBy = player;
        }

        cacheTable.put(player, new CacheData(newDamage, getConsecutiveHits(player)));
    }

    public void setConsecutiveHits(String player, int newConsecutiveHits) {
        if(!isRegistered(player)) {
            register(player);
            return;
        }

        cacheTable.put(player, new CacheData(getDamageDealt(player), newConsecutiveHits));
    }

    public double getDamageDealt(String player) {
        if(!isRegistered(player)) {
            register(player);
            return 0.0;
        }

        CacheData cacheData = cacheTable.get(player);
        return cacheData.getDamage();
    }

    public int getConsecutiveHits(String player) {
        if(!isRegistered(player)) {
            register(player);
            return 0;
        }

        CacheData cacheData = cacheTable.get(player);
        return cacheData.getConsecutiveHits();
    }

    public HashMap<String, CacheData> fetchResults() {
        return cacheTable;
    }

    public void reset() {
        cacheTable.clear();
    }
}
