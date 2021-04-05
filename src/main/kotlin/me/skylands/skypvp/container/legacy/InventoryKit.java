package me.skylands.skypvp.container.legacy;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.clan.inventory.InventoryEdit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryKit implements Listener {

    public static void open(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§0Kits");

        inventory.setItem(10, woolGold);
        inventory.setItem(13, woolDiamond);
        inventory.setItem(16, woolSky);

        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().contains("§0Kits")) {
            event.setCancelled(true);
            Player p = (Player) event.getWhoClicked();
            p.updateInventory();

            if (event.getCurrentItem() != null
                    && event.getCurrentItem().getType() != Material.AIR) {
                ItemStack itemStack = event.getCurrentItem();

                if (itemStack.equals(woolSky)) {
                    handleKit(p, "sky", "§7Spieler");
                }
                if (itemStack.equals(woolGold)) {
                    handleKit(p, "gold", "§eGold");
                }
                if (itemStack.equals(woolDiamond)) {
                    handleKit(p, "diamond", "§bDiamond");
                }
                if (itemStack.equals(woolEmerald)) {
                    handleKit(p, "emerald", "§aEmerald");
                }
            }
        }
    }

    private void handleKit(Player player, String kit, String required) {
        if (player.hasPermission("essentials.kits." + kit)) {
            player.performCommand("essentials:kit " + kit);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            player.closeInventory();
        } else {
            player.sendMessage(Messages.PREFIX + "§cFür dieses Kit benötigst du den Rang " + required);
            player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
            player.closeInventory();
        }
    }

    private static ItemStack woolSky = new InventoryEdit.ItemBuilderLegacy(Material.WOOL).damage((byte) 8)
            .name("§7Kit Sky")
            .lore(new String[]{"§7Verfügbar für: §7§oSpieler"})
            .build();
    private static ItemStack woolGold = new InventoryEdit.ItemBuilderLegacy(Material.WOOL).damage((byte) 4)
            .name("§6Kit Gold")
            .lore(new String[]{"§7§oVerfügbar für: §e§oGold"})
            .build();
    private static ItemStack woolDiamond = new InventoryEdit.ItemBuilderLegacy(Material.WOOL).damage((byte) 9)
            .name("§3Kit Diamond")
            .lore(new String[]{"§7Verfügbar für: §3§oDiamond"})
            .build();
    private static ItemStack woolEmerald = new InventoryEdit.ItemBuilderLegacy(Material.WOOL).damage((byte) 5)
            .name("§aKit Emerald")
            .lore(new String[]{"§7Verfügbar für: §a§oEmerald"})
            .build();

}
