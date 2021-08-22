package me.skylands.skypvp.task.pve;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveChestTask extends BukkitRunnable {

    private Location chestLocation;

    public RemoveChestTask(Location chestLocation) {
        this.chestLocation = chestLocation;
    }

    @Override
    public void run() {
        this.chestLocation.getWorld().getBlockAt(this.chestLocation).setType(Material.AIR);
        Bukkit.getLogger().info("Chest should be removed (after)");
    }
}
