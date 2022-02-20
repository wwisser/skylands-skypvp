package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.Totem;
import me.skylands.skypvp.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
                    if (!Helper.hasItem(player, z1EnterBossIslandTicket, 1)) {
                        if(!knownPlayers.contains(player.getName())) {
                            player.teleport(new Location(player.getWorld(), 238.5, 80, -51.5, 295, 0));
                            player.sendMessage(Messages.PREFIX + "Du besitzt keine §ePassiererlaubnis§7. Sammle §e25 Monstermarken§7 von jeder Insel.");
                        }
                    } else {
                        if(!knownPlayers.contains(player.getName())) {
                            player.sendMessage(Messages.PREFIX + "Willkommen zum §eBosskampf§7! Du hast nun für §eeine Stunde§7 Zugang zur Bossinsel. §7Besiege den §eSlimekönig§7 um Belohnungen zu erhalten.");
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
}