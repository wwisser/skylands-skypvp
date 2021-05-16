package me.skylands.skypvp.container.template.impl;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopContainerTemplate extends ContainerTemplate {

    static final ItemStack ITEM_BACK = new ItemBuilder(Material.BARRIER)
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

        final BloodpointsShopContainerTemplate bloodpointsTemplate = new BloodpointsShopContainerTemplate(
                super.containerService,
                this
        );

        builder.addAction(
            10,
            new ItemBuilder(Material.BOOK).name("§9§lRechte").build(),
            permissionShopTemplate::openContainer
        );
        builder.addAction(
            12,
            new ItemBuilder(Material.EMERALD).name("§a§lRänge").build(),
            rankShopTemplate::openContainer
        );

        builder.addAction(
                14,
                new ItemBuilder(Material.GOLDEN_APPLE).name("§e§lItems").build(),
                itemShopContainerTemplate::openContainer
        );

        builder.addAction(
                16,
                new ItemBuilder(Material.IRON_SWORD).name("§c§lBlutpunkte").build(),
                bloodpointsTemplate::openContainer
        );

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    protected void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }

}
