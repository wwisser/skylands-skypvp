package me.skylands.skypvp.clan.inventory;

import me.skylands.skypvp.clan.util.clan.ClanAccess;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryEdit implements Listener {

    public static String InventoryTitle = "Claneinstellungen bearbeiten";

    public static void open(Player p, ClanUser clanUser) {
        Inventory inventory = Bukkit.createInventory(null, 9, InventoryTitle);
        ClanAccess clanAccess = clanUser.getClan().getClanAccess();

        if (clanAccess.equals(ClanAccess.OPEN)) {
            inventory.setItem(4, accessOpen);
        }
        if (clanAccess.equals(ClanAccess.INVITE_ONLY)) {
            inventory.setItem(4, accessInviteOnly);
        }
        if (clanAccess.equals(ClanAccess.CLOSED)) {
            inventory.setItem(4, accessClosed);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType()
                    .equals(Material.AIR)) {
                inventory.setItem(i, glassGray);
            }
        }

        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent ev) {
        if (ev.getInventory().getTitle().equals(InventoryTitle)) {
            ev.setCancelled(true);
            Player p = (Player) ev.getWhoClicked();
            p.updateInventory();

            if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                ItemStack itemStack = ev.getCurrentItem();

                if (itemStack.equals(accessOpen)) {
                    p.chat("/clan edit access invite_only");
                    p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                    ev.getInventory().setItem(4, accessInviteOnly);
                } else if (itemStack.equals(accessInviteOnly)) {
                    p.chat("/clan edit access closed");
                    p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                    ev.getInventory().setItem(4, accessClosed);
                } else if (itemStack.equals(accessClosed)) {
                    p.chat("/clan edit access open");
                    p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                    ev.getInventory().setItem(4, accessOpen);
                }
            }
        }
    }

    private static ItemStack glassGray = new ItemBuilder(Material.STAINED_GLASS_PANE)
            .data((byte) 7).name(" ").build();
    private static ItemStack accessOpen = new ItemBuilder(Material.STAINED_CLAY).name("§6Beitritt")
            .modifyLore().set(new String[]{"§6> " + ClanAccess.OPEN.getName(),
                    "§7> " + ClanAccess.INVITE_ONLY.getName(),
                    "§7> " + ClanAccess.CLOSED.getName()}).finish().data((byte) 5).build();
    private static ItemStack accessInviteOnly = new ItemBuilder(Material.STAINED_CLAY)
            .name("§6Beitritt").modifyLore().set(new String[]{"§7> " + ClanAccess.OPEN.getName(),
                    "§6> " + ClanAccess.INVITE_ONLY.getName(),
                    "§7> " + ClanAccess.CLOSED.getName()}).finish().data((byte) 4).build();
    private static ItemStack accessClosed = new ItemBuilder(Material.STAINED_CLAY)
            .name("§6Beitritt").modifyLore().set(new String[]{"§7> " + ClanAccess.OPEN.getName(),
                    "§7> " + ClanAccess.INVITE_ONLY.getName(),
                    "§6> " + ClanAccess.CLOSED.getName()}).finish().data((byte) 14).build();

}
