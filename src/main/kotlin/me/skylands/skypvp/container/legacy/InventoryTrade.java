package me.skylands.skypvp.container.legacy;

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

public class InventoryTrade implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 2 * 9, "§0Verkauf");

        inventory.setItem(0, getSellOption(Material.WHEAT, 64, 12, 0));
        inventory.setItem(1, getSellOption(Material.SUGAR_CANE, 64, 8, 0));
        inventory.setItem(2, getSellOption(Material.PUMPKIN, 64, 8, 0));
        inventory.setItem(3, getSellOption(Material.CACTUS, 64, 1, 0));
        inventory.setItem(4, getSellOption(Material.INK_SACK, 64, 12, 3));
        inventory.setItem(5, getSellOption(Material.MELON, 64, 2, 0));
        inventory.setItem(6, getSellOption(Material.CARROT_ITEM, 64, 12, 0));
        inventory.setItem(7, getSellOption(Material.POTATO_ITEM, 64, 12, 0));

        inventory.setItem(9, getSellOption(Material.RAW_FISH, 1, 1, 0));
        inventory.setItem(10, getSellOption(Material.RAW_FISH, 1, 4, 1));
        inventory.setItem(11, getSellOption(Material.RAW_FISH, 1, 14, 2));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().contains("§0Verkauf")) {
            event.setCancelled(true);

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getType() != Material.AIR) {
                if (currentItem.hasItemMeta() && currentItem.getItemMeta().hasDisplayName()) {
                    if (event.getInventory()
                            .equals(event.getWhoClicked().getOpenInventory().getTopInventory())) {
                        Player player = (Player) event.getWhoClicked();
                        if (hasItem(player, currentItem.getType(), currentItem.getAmount(),
                                currentItem.getData().getData())) {
                            int price = getPrice(currentItem);
                            player.setLevel(player.getLevel() + price);
                            player.getInventory().removeItem(
                                    cleanItem(currentItem, currentItem.getData().getData()));
                            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
                            player.sendMessage(String.format("Du hast §e%sx %s §7für §a%s §7Level verkauft.",
                                    String.valueOf(currentItem.getAmount()),
                                    currentItem.getType().toString(),
                                    String.valueOf(price)));
                            System.out.println(String.format("[SELL-WATCHER] %s: %s %s", player.getName(),
                                    currentItem.getType().toString(),
                                    String.valueOf(currentItem.getAmount())));
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            player.sendMessage(String.format("Dafür brauchst du §e%s %s§7.",
                                    String.valueOf(currentItem.getAmount()),
                                    currentItem.getType().toString()));
                        }
                    }
                }
            }
        }
    }

    private static ItemStack getSellOption(Material material, int amount, int price, int damage) {
        return new InventoryEdit.ItemBuilderLegacy(material).name("§eVerkaufen: " + amount + "x " + material.toString())
                .lore(new String[]{"", "§7Wert:§e " + price + " Level", "§7Klicke zum Verkaufen"})
                .damage(damage).amount(amount).build();
    }

    private int getPrice(ItemStack itemStack) {
        for (String lore : itemStack.getItemMeta().getLore()) {
            if (lore.contains("Wert")) {
                return Integer.valueOf(lore.split(" ")[1]);
            }
        }
        return 0;
    }

    private ItemStack cleanItem(ItemStack itemStack, int damage) {
        return new InventoryEdit.ItemBuilderLegacy(itemStack.getType()).damage(damage).amount(itemStack.getAmount())
                .build();
    }

    private boolean hasItem(Player player, Material material, int amount, int damage) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType().equals(material)
                    && itemStack.getData().getData() == (byte) damage && !itemStack.getItemMeta()
                    .hasDisplayName()) {
                count += itemStack.getAmount();
            }
        }
        return (count >= amount);
    }

}
