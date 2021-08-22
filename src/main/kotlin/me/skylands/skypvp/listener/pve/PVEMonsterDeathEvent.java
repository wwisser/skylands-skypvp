package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.SkyLands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PVEMonsterDeathEvent implements Listener {

    private HashMap<ItemStack, Double> dropTable = loadDropTable();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld() == SkyLands.WORLD_SKYPVP &! (event.getEntity() instanceof Player)) {
            int maxXP = event.getEntityType().equals(EntityType.MAGMA_CUBE) || event.getEntityType().equals(EntityType.SLIME) ? 25 : 250;
            event.setDroppedExp((int) (Math.random() * maxXP) + 1);
            event.getDrops().clear();
            event.getDrops().add(drawRandomDrop());
        }
    }

    private ItemStack drawRandomDrop() {
        double ticket = Math.random();
        double accu = 0.0;
        for (ItemStack itemStack : dropTable.keySet()) {
            accu += dropTable.get(itemStack);
            if (ticket <= accu) {
                return itemStack;
            }
        }
        return null;
    }

    private HashMap<ItemStack, Double> loadDropTable() {
        HashMap<ItemStack, Double> dropTable = new HashMap<>();

        dropTable.put(new ItemStack(Material.GOLDEN_APPLE, 2), 0.1);
        dropTable.put(new ItemStack(Material.DIAMOND, 1), 0.1);
        dropTable.put(new ItemStack(Material.IRON_INGOT, 2), 0.1);
        dropTable.put(new ItemStack(Material.ENDER_PEARL, 1), 0.1);
        dropTable.put(new ItemStack(Material.AIR), 0.6);

        return dropTable;
    }
}
