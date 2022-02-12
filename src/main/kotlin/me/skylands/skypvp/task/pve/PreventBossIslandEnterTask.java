package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Totem;
import me.skylands.skypvp.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PreventBossIslandEnterTask extends BukkitRunnable {

    private final Collection<Totem> totems = SkyLands.totemConfig.getBossTotems().values();
    private final ItemStack z1EnterBossIslandTicket = new ItemBuilder(Material.BOOK).glow().name("§aPassiererlaubnis").modifyLore().add("§7Passiererlaubnis für §aZone 1").add(" ").add("§7Mit dieser §ePassiererlaubnis").add("§7gelangst Du zum §eBosskampf§7.").finish().build();
    private final List<String> knownPlayers = new ArrayList<String>();

    @Override
    public void run() {
        for (Totem totem : totems) {
            if(totem.getBossType().equals("None")) return;

            for (Player player : SkyLands.WORLD_SKYPVP.getPlayers()) {
                if (totem.getCenterLocation().distance(player.getLocation()) < (totem.getSpawnRadius() + 25)) {
                    if (!hasItem(player, z1EnterBossIslandTicket, 1)) {
                        if(!knownPlayers.contains(player.getName())) {
                            player.setVelocity(player.getLocation().getDirection()
                                    .setY(.3).multiply(.3)
                                    .setX(-.1).multiply(1.5)
                                    .setZ(0.75).multiply(3)
                            );
                            player.sendMessage(Messages.PREFIX + "Du besitzt keine §ePassiererlaubnis§7. Sammle §e25 Monstermarken§7 von jeder Insel.");
                        }
                    } else {
                        if(!knownPlayers.contains(player.getName())) {
                            player.sendMessage(Messages.PREFIX + "Willkommen zum §eBosskampf§7! Besiege den §eSlimekönig§7 um Belohnungen zu erhalten.");
                            knownPlayers.add(player.getName());
                            player.getInventory().removeItem(z1EnterBossIslandTicket);

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SkyLands.plugin, () -> {
                                knownPlayers.remove(player.getName());
                            }, 1200*60L);
                        }
                    }
                }
            }
        }
    }


    private boolean hasItem(Player player, ItemStack query, int amount) {
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
}