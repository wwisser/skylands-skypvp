package me.skylands.skypvp.clan.inventory;

import me.skylands.skypvp.clan.util.clan.ClanAccess;
import me.skylands.skypvp.clan.util.clan.ClanUser;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;

public class InventoryEdit implements Listener {

    public static String InventoryTitle = "§0Claneinstellungen bearbeiten";

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

    private static ItemStack glassGray = new ItemBuilderLegacy(Material.STAINED_GLASS_PANE)
            .damage((byte) 7).name(" ").build();
    private static ItemStack accessOpen = new ItemBuilderLegacy(Material.STAINED_CLAY).name("§6Beitritt")
            .lore(new String[]{"§6> " + ClanAccess.OPEN.getName(),
                    "§7> " + ClanAccess.INVITE_ONLY.getName(),
                    "§7> " + ClanAccess.CLOSED.getName()}).damage((byte) 5).build();
    private static ItemStack accessInviteOnly = new ItemBuilderLegacy(Material.STAINED_CLAY)
            .name("§6Beitritt").lore(new String[]{"§7> " + ClanAccess.OPEN.getName(),
                    "§6> " + ClanAccess.INVITE_ONLY.getName(),
                    "§7> " + ClanAccess.CLOSED.getName()}).damage((byte) 4).build();
    private static ItemStack accessClosed = new ItemBuilderLegacy(Material.STAINED_CLAY)
            .name("§6Beitritt").lore(new String[]{"§7> " + ClanAccess.OPEN.getName(),
                    "§7> " + ClanAccess.INVITE_ONLY.getName(),
                    "§6> " + ClanAccess.CLOSED.getName()}).damage((byte) 14).build();


    private static class ItemBuilderLegacy {

        private Material material;
        private int amount;
        private String name;
        private int damage;
        private HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        private String ownerName;
        private int color1;
        private int color2;
        private int color3;
        private String[] lore;

        public ItemBuilderLegacy(Material material) {
            this.material = material;
            this.amount = 1;
            this.damage = 0;
        }

        public ItemBuilderLegacy amount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemBuilderLegacy name(String name) {
            this.name = "§r" + name;
            return this;
        }

        public ItemBuilderLegacy damage(int damage) {
            this.damage = damage;
            return this;
        }

        public ItemBuilderLegacy enchantment(Enchantment enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }

        public ItemBuilderLegacy head(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public ItemBuilderLegacy leatherColor(int color1, int color2, int color3) {
            this.color1 = color1;
            this.color2 = color2;
            this.color3 = color3;
            return this;
        }

        public ItemBuilderLegacy lore(String[] lore) {
            this.lore = lore;
            return this;
        }

        public ItemStack build() {
            ItemStack itemStack = new ItemStack(this.material, this.amount, (byte) this.damage);

            if (!this.enchantments.isEmpty()) {
                itemStack.addUnsafeEnchantments(enchantments);
            }

            ItemMeta itemMeta = itemStack.getItemMeta();

            if (this.name != null) {
                itemMeta.setDisplayName(name);
            }

            if (this.ownerName != null) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                skullMeta.setOwner(ownerName);
                itemStack.setItemMeta(skullMeta);
            }

            if (this.lore != null) {
                itemMeta.setLore(Arrays.asList(this.lore));
            }

            itemStack.setItemMeta(itemMeta);

            if (itemStack.getType().toString().startsWith("LEATHER_")) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

                leatherArmorMeta.setColor(Color.fromRGB(this.color1, this.color2, this.color3));
                itemStack.setItemMeta(leatherArmorMeta);
            }

            return itemStack;
        }

    }

}
