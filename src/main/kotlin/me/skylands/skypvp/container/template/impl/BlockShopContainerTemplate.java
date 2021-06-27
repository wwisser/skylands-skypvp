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

public class BlockShopContainerTemplate extends ContainerTemplate {

    private static final String BUY_DESCRIPTION = "§7Rechtsklick, um das Item zu kaufen.";

    private static final List<ItemBuyOption> OPTIONS = Stream.of(
            new ItemBuyOption(new ItemBuilder(Material.GRASS).name("§fGrass").amount(16).build(), 5, 10),
            new ItemBuyOption(new ItemBuilder(Material.DIRT).name("§fErde").amount(16).build(), 5, 11),
            new ItemBuyOption(new ItemBuilder(Material.STONE).name("§fStein").amount(16).build(), 5, 12),
            new ItemBuyOption(new ItemBuilder(Material.COBBLESTONE).name("§fCobblestone").amount(16).build(), 5, 13),
            new ItemBuyOption(new ItemBuilder(Material.STONE).name("§fGranit").amount(16).data((short) 1).build(), 5, 14),
            new ItemBuyOption(new ItemBuilder(Material.STONE).name("§fDiorit").amount(16).data((short) 3).build(), 5, 15),
            new ItemBuyOption(new ItemBuilder(Material.STONE).name("§fAndesit").amount(16).data((short) 5).build(), 5, 16),

            new ItemBuyOption((new ItemBuilder(Material.LOG).name("§fEichenholz").amount(16).build()), 5, 19),
            new ItemBuyOption((new ItemBuilder(Material.LOG).name("§fFichtenholz").amount(16).data((short) 1).build()), 5, 20),
            new ItemBuyOption((new ItemBuilder(Material.LOG).name("§fBirkenholz").amount(16).data((short) 2).build()), 5, 21),
            new ItemBuyOption((new ItemBuilder(Material.LOG).name("§fDschungelholz").amount(16).data((short) 3).build()), 5, 22),
            new ItemBuyOption((new ItemBuilder(Material.LOG_2).name("§fAkazienholz").amount(16).build()), 5, 23),
            new ItemBuyOption((new ItemBuilder(Material.LOG_2).name("§fSchwarzeichenholz").amount(16).data((short) 1).build()), 5, 24),
            new ItemBuyOption((new ItemBuilder(Material.LEAVES).name("§fEichenblätter").amount(16).build()), 5, 25),

            new ItemBuyOption((new ItemBuilder(Material.SNOW_BLOCK).name("§fSchnee").amount(16).build()), 5, 28),
            new ItemBuyOption((new ItemBuilder(Material.WOOL).name("§fWolle").amount(16).build()), 5, 29),
            new ItemBuyOption((new ItemBuilder(Material.CLAY).name("§fTon").amount(16).build()), 5, 30),
            new ItemBuyOption((new ItemBuilder(Material.ICE).name("§fEis").amount(16).build()), 5, 31),
            new ItemBuyOption((new ItemBuilder(Material.PACKED_ICE).name("§fPackeis").amount(16).build()), 5, 32),
            new ItemBuyOption((new ItemBuilder(Material.SAND).name("§fSand").amount(16).build()), 5, 33),
            new ItemBuyOption((new ItemBuilder(Material.SANDSTONE).name("§fSandstein").amount(16).build()), 5, 34)

    ).collect(Collectors.toList());

    private final ShopContainerTemplate shopContainerTemplate;
    private final Container container;

    BlockShopContainerTemplate(ContainerManager containerService, final ShopContainerTemplate shopContainerTemplate) {
        super(containerService);
        this.shopContainerTemplate = shopContainerTemplate;

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lBlöcke")
                .setStorageLevel(ContainerStorageLevel.STORED)
                .setSize(5 * 9)
                .addAction(44, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer);

        for (ItemBuyOption option : OPTIONS) {
            ItemStack itemStack = option.getItemStack().clone();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Arrays.asList("", "§7Preis: §a" + option.getCost() + " Level", BUY_DESCRIPTION));
            itemStack.setItemMeta(itemMeta);

            builder.addAction(option.getSlot(), itemStack, new PurchaseContainerAction(clicker -> {
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
