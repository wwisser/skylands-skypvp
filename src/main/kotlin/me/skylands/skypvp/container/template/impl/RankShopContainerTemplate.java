package me.skylands.skypvp.container.template.impl;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.PremiumRank;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.action.impl.PurchaseContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.util.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RankShopContainerTemplate extends ContainerTemplate {
    private ShopContainerTemplate shopContainerTemplate;

    RankShopContainerTemplate(final ContainerManager containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;
    }

    @Override
    protected void openContainer(Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lRänge")
            .addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.NEW);

        int count = 10;
        for (PremiumRank rank : PremiumRank.values()) {
            final boolean permission = player.hasPermission(rank.getPermission());

            ContainerAction action = permission
                ? ContainerAction.NONE
                : new PurchaseContainerAction(clicker -> {
                    if (PremiumRank.getCurrentCosts(clicker, rank) == 0) {
                        clicker.sendMessage(Messages.PREFIX + "§cDu hast diesen Rang bereits.");
                        return;
                    }


                PermissionUtils.setRank(clicker.getName(), rank.getGroupName());
                Bukkit.broadcastMessage(
                    Messages.PREFIX
                        + "§e" + clicker.getName()
                        + " §7hat sich den Rang " + rank.getChatColor() + ChatColor.BOLD + rank.getName() + " §7gekauft.");
                Bukkit.broadcastMessage(Messages.PREFIX + "Jetzt auch mit Leveln einkaufen §8§l=> §d§l/levelshop");
            }, PremiumRank.getCurrentCosts(player, rank), true);

            ItemStack itemStack = new ItemBuilder(rank.getMaterial())
                .name("§7Rang " + rank.getChatColor() + "§l" + rank.getName())
                .modifyLore()
                .add("")
                .add(PremiumRank.PERMISSIONS_DEFAULT)
                .add(rank.getPermissions())
                .add("")
                .add(
                    permission
                        ? "§aBereits gekauft"
                        : "§7Preis: §a" + PremiumRank.getCurrentCosts(player, rank) + " Level"
                )
                .finish()
                .build();

            builder.addAction(count, itemStack, action);

            count += 3;
        }

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }

}
