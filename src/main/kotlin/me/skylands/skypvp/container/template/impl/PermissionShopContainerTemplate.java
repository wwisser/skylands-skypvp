package me.skylands.skypvp.container.template.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.action.impl.PurchaseContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.util.PermissionUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PermissionShopContainerTemplate extends ContainerTemplate {

    private Map<ShopPermission, ContainerAction> purchaseActions = new HashMap<>();

    private ShopContainerTemplate shopContainerTemplate;

    PermissionShopContainerTemplate(final ContainerManager containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;

        for (ShopPermission shopPermission : ShopPermission.values()) {
            this.purchaseActions.put(
                shopPermission,
                new PurchaseContainerAction(
                    player -> {
                        PermissionUtils.addPermission(player.getName(), shopPermission.getPermission());
                        Bukkit.broadcastMessage(
                            Messages.PREFIX
                                + "§e" + player.getName()
                                + " §7hat sich Rechte für §e/" + shopPermission.getCommand() + " §7gekauft.");
                        Bukkit.broadcastMessage(Messages.PREFIX + "Jetzt auch mit Leveln einkaufen §8§l=> §d§l/levelshop");
                    },
                    shopPermission.getCosts()
                )
            );
        }
    }

    @Override
    protected void openContainer(final Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lRechte")
            .addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.NEW);

        int count = 10;
        for (ShopPermission shopPermission : ShopPermission.values()) {
            final boolean permission = player.hasPermission(shopPermission.getPermission());

            ContainerAction action = permission
                ? ContainerAction.NONE
                : this.purchaseActions.get(shopPermission);

            ItemStack itemStack = new ItemBuilder(shopPermission.getMaterial())
                .name("§9§l" + shopPermission.getName())
                .modifyLore()
                .add("")
                .add(
                    permission
                        ? "§aBereits gekauft"
                        : "§7Preis: §a" + shopPermission.getCosts() + " Level"
                )
                .finish()
                .build();

            builder.addAction(count, itemStack, action);

            count += 2;
        }

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }

    @AllArgsConstructor
    @Getter
    private enum ShopPermission {
        REPAIR(Material.ANVIL, 5000, "essentials.repair"),
        STACK(Material.GLASS_BOTTLE, 5000, "worldguard.stack"),
        INVSEE(Material.SIGN, 5000, "essentials.invsee"),
        FLY(Material.FEATHER, 7500, "essentials.fly");

        private Material material;
        private int costs;
        private String permission;

        public String getCommand() {
            return this.toString().toLowerCase();
        }

        public String getPermission() {
            return this.permission;
        }

        public String getName() {
            return StringUtils.capitalize(this.getCommand());
        }

    }

}
