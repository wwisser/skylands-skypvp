package me.skylands.skypvp.container.template.impl.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TradeTokensContainerTemplate extends ContainerTemplate {

    private final Container container;
    private final ItemStack z1MainIslandTicket = new ItemBuilder(Material.PAPER).glow().name("§eMonstermarke").modifyLore().add("§7Monstermarke der §eHauptinsel").add(" ").add("§7Du benötigst §e25§7 Marken für").add("§7eine §ePassiererlaubnis§7.").finish().amount(1).build();
    private final ItemStack z1PvPIslandTicket = new ItemBuilder(Material.PAPER).glow().name("§eMonstermarke").modifyLore().add("§7Monstermarke der §ePvP-Insel").add(" ").add("§7Du benötigst §e25§7 Marken für").add("§7eine §ePassiererlaubnis§7.").finish().build();
    private final ItemStack z1EnterBossIslandTicket = new ItemBuilder(Material.BOOK).glow().name("§aPassiererlaubnis").modifyLore().add("§7Passiererlaubnis für §aZone 1").add(" ").add("§7Mit dieser §ePassiererlaubnis").add("§7gelangst Du zum §eBosskampf§7.").finish().build();

    public TradeTokensContainerTemplate(ContainerManager containerManager) {
        super(containerManager);

        ItemStack tradeButton = new ItemBuilder(Material.BOOK_AND_QUILL).name("§eJetzt tauschen!").modifyLore().add(" ").add("§7Tausche deine §eMonstermarken §7ein").add("§7und erhalte eine §ePassiererlaubnis.").finish().glow().build();
        ItemStack explanation = new ItemBuilder(Material.PAPER).name("§aHilfe").modifyLore().add(" ").add("§7Um zum §eBosskampf §7zugelassen zu werden,").add("§7benötigst du §e25 Monstermarken").add("§7von jeder regulären Insel.").add(" ").add("§7Du erhältst §eMonstermarken§7, indem").add("§7du §eMonster§7 tötest.").add(" ").add("§7Melde dich zum §eBosskampf§7 an").add("§7und erhalte §etolle Belohnungen§7!").finish().build();

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lPvE §0> §0§lDorfbewohner").setStorageLevel(ContainerStorageLevel.NEW);

        builder.addAction(13, tradeButton,
                player -> {
                    Inventory pInv = player.getInventory();
                    if (hasItem(player, z1MainIslandTicket, 25) && hasItem(player, z1PvPIslandTicket, 25)) {
                        ItemStack mainIslandTicketRemoval = z1MainIslandTicket.clone();
                        ItemStack pvpIslandTicketRemoval = z1PvPIslandTicket.clone();
                        pvpIslandTicketRemoval.setAmount(25);
                        mainIslandTicketRemoval.setAmount(25);

                        pInv.removeItem(mainIslandTicketRemoval);
                        pInv.removeItem(pvpIslandTicketRemoval);

                        pInv.addItem(z1EnterBossIslandTicket);
                        player.closeInventory();
                        player.sendMessage(Messages.PREFIX + "§7Du hast erfolgreich §eMonstermarken§7 gegen eine §ePassiererlaubnis§7 eingetauscht. Du kannst dich jetzt mit einer §eEnderperle§7 zur §eBossinsel §7teleportieren.");
                    } else {
                        player.closeInventory();
                        player.sendMessage(Messages.PREFIX + "§7Du besitzt nicht genug §eMonstermarken§7! Du benötigst §e25§7 von jeder Insel.");
                    }
                });
        builder.addAction(26, explanation, ContainerAction.NONE);

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    private boolean hasItem(Player player, ItemStack query, int amount) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack != null) {
                if(itemStack.getType().equals(query.getType()) && itemStack.getItemMeta().equals(query.getItemMeta())) {
                    count += itemStack.getAmount();
                }
            }
        }
        return (count >= amount);
    }

    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }
}