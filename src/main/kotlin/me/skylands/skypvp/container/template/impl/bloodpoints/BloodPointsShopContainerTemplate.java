package me.skylands.skypvp.container.template.impl.bloodpoints;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BloodPointsShopContainerTemplate extends ContainerTemplate {

    private final Container container;
    private final ShopContainerTemplate shopContainerTemplate;

    public BloodPointsShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);
        this.shopContainerTemplate = shopContainerTemplate;

        ItemStack Effects = new ItemBuilder(Material.POTION).name("§b§lInseleffekte").build();
        ItemStack SpecialItems = new ItemBuilder(Material.NETHER_STAR).glow().name("§e§lBesondere Items").build();
        ItemStack Upgrades = new ItemBuilder(Material.DIAMOND_PICKAXE).name("§d§lUpgrades").build();

        ItemMeta itemMeta = Upgrades.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        Upgrades.setItemMeta(itemMeta);

        final SpecialItemsShopContainerTemplate specialItemsTemplate = new SpecialItemsShopContainerTemplate(
                super.containerService,
                this.shopContainerTemplate
        );

        final IslandEffectShopContainerTemplate islandEffectShopContainerTemplate = new IslandEffectShopContainerTemplate(
                super.containerService,
                this.shopContainerTemplate
        );

        final UpgradesShopContainerTemplate upgradesShopContainerTemplate = new UpgradesShopContainerTemplate(
                super.containerService,
                this.shopContainerTemplate
        );

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lBlutpunkte")
                .setStorageLevel(ContainerStorageLevel.NEW)
                .addAction(26, ShopContainerTemplate.ITEM_BACK, shopContainerTemplate::openContainer);

        builder.addAction(10, Upgrades, upgradesShopContainerTemplate::openContainer);
        builder.addAction(13, SpecialItems, specialItemsTemplate::openContainer);
        builder.addAction(16, Effects, islandEffectShopContainerTemplate::openContainer);

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }
    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }
}