package me.skylands.skypvp.container.template.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.impl.PurchaseContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemShopContainerTemplate extends ContainerTemplate {

    private static final String BUY_DESCRIPTION = "§7Rechtsklick, um das Item zu kaufen.";

    private static final List<ItemBuyOption> OPTIONS = Stream.of(
            new ItemBuyOption(new ItemBuilder(Material.DIAMOND_HELMET).name("§bDiamant Helm").build(), 20, 0),
            new ItemBuyOption(new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("§bDiamant Chestplate").build(), 20, 1),
            new ItemBuyOption(new ItemBuilder(Material.DIAMOND_LEGGINGS).name("§bDiamant Hose").build(), 20, 2),
            new ItemBuyOption(new ItemBuilder(Material.DIAMOND_BOOTS).name("§bDiamant Schuhe").build(), 20, 3),

            new ItemBuyOption(new ItemBuilder(Material.GOLDEN_APPLE).name("§eGoldapfel").build(), 7, 9),
            new ItemBuyOption(new ItemBuilder(Material.ENDER_PEARL).name("§2Enderperle").build(), 7, 10),
            new ItemBuyOption(new ItemBuilder(Material.DIAMOND_SWORD).name("§eGoldapfel").build(), 7, 11),
            new ItemBuyOption(new ItemBuilder(Material.FISHING_ROD).name("§cAngel").build(), 5, 12),

            new ItemBuyOption(new ItemBuilder(Material.BLAZE_ROD).name("§6Blaze Rod").build(), 30, 5),
            new ItemBuyOption(new ItemBuilder(Material.SPECKLED_MELON).name("§eGoldene Melone").build(), 12, 6),
            new ItemBuyOption(new ItemBuilder(Material.NETHER_STALK).name("§4Netherwarze").build(), 20, 7),
            new ItemBuyOption(new ItemBuilder(Material.MAGMA_CREAM).name("§aMagma Creme").build(), 12, 8),

            new ItemBuyOption(new ItemBuilder(Material.POTION).name("§1Wasserflasche").build(), 5, 14),
            new ItemBuyOption(new ItemBuilder(Material.GLOWSTONE_DUST).name("§eGlowstone Staub").build(), 15, 15),
            new ItemBuyOption(new ItemBuilder(Material.BLAZE_POWDER).name("§6Blazepowder").build(), 15, 16),
            new ItemBuyOption(new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name("§4Fermentiertes Spinnenauge").build(), 12, 17),

            new ItemBuyOption(new ItemBuilder(Material.CACTUS).name("§2Kaktus").build(), 6, 27),
            new ItemBuyOption(new ItemBuilder(Material.SAPLING).name("§2Setzling").build(), 6, 28),
            new ItemBuyOption(new ItemBuilder(Material.SUGAR_CANE).name("§aZuckerrohr").build(), 6, 29),

            new ItemBuyOption(new ItemBuilder(Material.ANVIL).name("§7Amboss").build(), 20, 33),
            new ItemBuyOption(new ItemBuilder(Material.ENCHANTMENT_TABLE).name("§dZaubertisch").build(), 30, 34),
            new ItemBuyOption(new ItemBuilder(Material.ENDER_CHEST).name("§2Ender Truhe").build(), 50, 35)
            ).collect(Collectors.toList());

    private final ShopContainerTemplate shopContainerTemplate;
    private final Container container;

    ItemShopContainerTemplate(ContainerManager containerService, final ShopContainerTemplate shopContainerTemplate) {
        super(containerService);
        this.shopContainerTemplate = shopContainerTemplate;

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lItems")
                .setStorageLevel(ContainerStorageLevel.STORED)
                .setSize(6 * 9)
                .addAction(53, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer);

        for (ItemBuyOption option : OPTIONS) {
            ItemMeta itemMeta = option.getItemStack().getItemMeta();
            itemMeta.setLore(Arrays.asList("", "§7Preis: §a" + option.getCost() + " Level", BUY_DESCRIPTION));

            builder.addAction(option.getSlot(), option.getItemStack(), new PurchaseContainerAction(clicker -> {
                ItemUtils.INSTANCE.addAndDropRest(clicker, option.toClearItem());
                clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
            }, option.getCost(), false));
        }

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    protected void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }

    @AllArgsConstructor
    @Getter
    private static class ItemBuyOption {

        private ItemStack itemStack;
        private int cost;
        private int slot;

        private ItemStack toClearItem() {
            ItemStack cleanItem = this.itemStack.clone();
            ItemMeta itemMeta = cleanItem.getItemMeta();
            itemMeta.setLore(null);
            itemMeta.setDisplayName(null);
            cleanItem.setItemMeta(itemMeta);
            return cleanItem;
        }

    }

}
