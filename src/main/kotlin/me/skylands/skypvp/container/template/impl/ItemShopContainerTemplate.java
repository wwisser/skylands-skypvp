package me.skylands.skypvp.container.template.impl;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import org.bukkit.entity.Player;

public class ItemShopContainerTemplate extends ContainerTemplate {

    private final ShopContainerTemplate shopContainerTemplate;
    private final Container container;

     ItemShopContainerTemplate(ContainerManager containerService, final ShopContainerTemplate shopContainerTemplate) {
        super(containerService);
        this.shopContainerTemplate = shopContainerTemplate;

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop")
                .setStorageLevel(ContainerStorageLevel.STORED)
                .setSize(6 * 9)
                .addAction(53, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer);


        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    protected void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }

}
