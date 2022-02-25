package me.skylands.skypvp.pve;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.data.BossData;
import me.skylands.skypvp.pve.data.CacheData;
import me.skylands.skypvp.pve.data.StatsData;
import me.skylands.skypvp.user.UserService;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

public class Helper {

    public static HashMap<Integer, BossData> bossData = new HashMap<Integer, BossData>();
    private static HashMap<String, Integer> witchCache = new HashMap<String, Integer>();
    private static boolean totemsToggled = true;
    private static boolean silentRemoval = false;
    private static boolean debugMode = false;
    private static LinkedList<String> frozenPlayers = new LinkedList<String>();

    private static int dataStatus = 0; // 0 = To be processed | 1 = Processed
    private static HashMap<String, CacheData> unprocessedData;
    private static HashMap<String, StatsData> processedData = new HashMap<>();

    private static LinkedList<String> bpConverterPotion = new LinkedList<>();

    private final UserService userService = SkyLands.userService;

    public Hologram getHologramAt(Location location) {
        for(Hologram holo : HologramsAPI.getHolograms(SkyLands.plugin)) {
            if(holo.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation())) {
                return holo;
            }
        }

        return null;
    }

    public void addPlayerToConverterPotion(String player) {
        bpConverterPotion.add(player);
    }

    public void removePlayerFromConverterPotion(String player) {
        bpConverterPotion.remove(player);
    }

    public boolean hasConverterPotion(String player) {
        return bpConverterPotion.contains(player);
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int newStatus) {
        dataStatus = newStatus;
    }

    public HashMap<String, StatsData> getProcessedData() {
        if(dataStatus == 1) {
            return processedData;
        }
        return null;
    }

    public List<String> getParticipants() {
        if(getDataStatus() == 1) {
            return new ArrayList<String>(getProcessedData().keySet());
        }
        return null;
    }

    public void setUnprocessedData(HashMap<String, CacheData> data) {
        unprocessedData = data;
    }

    public HashMap<String, CacheData> getUnprocessedData() {
        if(getDataStatus() == 0) {
            return unprocessedData;
        }
        return null;
    }

    public void issueRewards() {
        if(getDataStatus() == 1) {
            getParticipants().forEach(entry -> {
                if(Bukkit.getServer().getPlayerExact(entry) != null) {
                    Player ePlayer = Bukkit.getServer().getPlayerExact(entry);
                    ePlayer.sendMessage(Messages.PREFIX + "Du hast erfolgreich am §eBosskampf§7 teilgenommen. §e+150 Level");
                    ePlayer.setLevel(ePlayer.getLevel() + 150);
                }
            });

            String participants = String.join("§7,§e ", getParticipants());
            BaseComponent[] msg = new ComponentBuilder(Messages.PREFIX + "Der §eSlimekönig§7 der §aZone 1§7 wurde von §e" + participants + " §7besiegt! ").append("[STATISTIKEN]").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bstats")).create();
            Bukkit.getServer().spigot().broadcast(msg);
        }
    }

    public void processData() {
        if(dataStatus == 0) {
            processedData.clear();
            HashMap<String, CacheData> results = unprocessedData;
            double totalDamageDealt = 0;
            DecimalFormat decimalFormatPercentage = new DecimalFormat("#.##");
            DecimalFormat decimalFormatDamageDealt = new DecimalFormat("#.#");

            for (CacheData value : results.values()) {
                double damageDealt = value.getDamage();
                totalDamageDealt += damageDealt;
            }

            for (Map.Entry<String, CacheData> entry : results.entrySet()) {
                String player = entry.getKey();
                CacheData value = entry.getValue();
                double damageDealt = value.getDamage() / 2;
                String formattedPercentage = decimalFormatPercentage.format((damageDealt / (totalDamageDealt / 2)) * 100);
                Bukkit.getLogger().info(player + " formattedPercentage = " + formattedPercentage);
                String formattedDamageDealt = decimalFormatDamageDealt.format(damageDealt);

                if(!(damageDealt <= 3)) {// If done enough dmg
                    processedData.put(player, new StatsData(formattedPercentage, formattedDamageDealt));
                }
            }
            dataStatus = 1;
            issueRewards();
        }
    }

    public static boolean getTotemsToggled() {
        return totemsToggled;
    }

    public static boolean getDebugMode() {
        return debugMode;
    }

    public static boolean getSilentRemovalStatus() {
        return silentRemoval;
    }

    public static int getWitchCacheStatus(String pName) {
        if(witchCache.containsKey(pName)) {
            return witchCache.get(pName);
        }
        return -1;
    }

    public static LinkedList<String> getFrozenPlayers() {
        return frozenPlayers;
    }

    public static void freeze(String player) {
        frozenPlayers.add(player);
    }

    public static void unfreeze(String player) {
        frozenPlayers.remove(player);
    }

    public static void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public static void toggleTotems() {
        totemsToggled = !totemsToggled;
    }

    public static void setSilentRemoval(boolean newSilentRemoval) {
        silentRemoval = newSilentRemoval;
    }

    public static void setWitchCacheData(String pName, int status) {
        witchCache.put(pName, status);
    }

    public static boolean hasItem(Player player, ItemStack query, int amount) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack != null) {
                if(itemStack.getType().equals(query.getType()) && itemStack.getItemMeta().equals(query.getItemMeta())) {
                    count += itemStack.getAmount();
                }
            }
        }
        return (count >= amount);
    }

    // Import
    public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        try {

            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
