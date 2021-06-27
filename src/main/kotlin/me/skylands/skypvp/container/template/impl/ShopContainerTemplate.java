package me.skylands.skypvp.container.template.impl;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.bloodpoints.BloodPointsShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopContainerTemplate extends ContainerTemplate {

    public static final ItemStack ITEM_BACK = new ItemBuilder(Material.BARRIER)
        .name("§cZurück zur Shopübersicht")
        .build();

    private Container container;

    public ShopContainerTemplate(ContainerManager containerService) {
        super(containerService);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop")
            .setStorageLevel(ContainerStorageLevel.STORED);

        final RankShopContainerTemplate rankShopTemplate = new RankShopContainerTemplate(
            super.containerService,
            this
        );
        final PermissionShopContainerTemplate permissionShopTemplate = new PermissionShopContainerTemplate(
            super.containerService,
            this
        );

        final ItemShopContainerTemplate itemShopContainerTemplate = new ItemShopContainerTemplate(
                super.containerService,
                this
        );

        final BloodPointsShopContainerTemplate bloodpointsTemplate = new BloodPointsShopContainerTemplate(
                super.containerService,
                this
        );

        ItemStack bloodPoints = new ItemBuilder(Material.IRON_SWORD).name("§c§lBlutpunkte").build();
        ItemMeta itemMeta = bloodPoints.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bloodPoints.setItemMeta(itemMeta);

        final BlockShopContainerTemplate blockShopContainerTemplate = new BlockShopContainerTemplate(
                super.containerService,
                this
        );

        builder.addAction(
            9,
            new ItemBuilder(Material.BOOK).name("§9§lRechte").build(),
            permissionShopTemplate::openContainer
        );
        builder.addAction(
            11,
            new ItemBuilder(Material.EMERALD).name("§a§lRänge").build(),
            rankShopTemplate::openContainer
        );

        builder.addAction(
                13,
                new ItemBuilder(Material.GOLDEN_APPLE).name("§e§lItems").build(),
                itemShopContainerTemplate::openContainer
        );

        builder.addAction(
                15,
                new ItemBuilder(Material.GRASS).name("§b§lBlöcke").build(),
                blockShopContainerTemplate::openContainer
        );

        builder.addAction(
                17,
                bloodPoints,
                bloodpointsTemplate::openContainer
        );

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }

}
