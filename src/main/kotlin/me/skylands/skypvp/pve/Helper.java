package me.skylands.skypvp.pve;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.skylands.skypvp.SkyLands;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Helper {

    public static HashMap<Integer, BossData> bossData = new HashMap<Integer, BossData>();
    private static HashMap<String, Integer> witchCache = new HashMap<String, Integer>();
    private static boolean totemsToggled = true;
    private static boolean silentRemoval = false;
    private static boolean debugMode = false;

    public void createBossHologram(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);
        Location totemCenterLoc = bossData.getTotemCenterLocation();
        Location holoLoc = new Location(SkyLands.WORLD_SKYPVP, totemCenterLoc.getX(),totemCenterLoc.getY() + 3,totemCenterLoc.getZ());

        Hologram hologram = HologramsAPI.createHologram(SkyLands.plugin, holoLoc);
        hologram.appendTextLine("LOADING..");
    }

    public Hologram getHologramAt(Location location) {
        for(Hologram holo : HologramsAPI.getHolograms(SkyLands.plugin)) {
            if(holo.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation())) {
                return holo;
            }
        }

        return null;
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
