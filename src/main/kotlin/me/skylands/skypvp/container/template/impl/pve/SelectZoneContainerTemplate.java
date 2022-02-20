package me.skylands.skypvp.container.template.impl.pve;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SelectZoneContainerTemplate extends ContainerTemplate {

    private final Container container;
    private final Location zone1 = new Location(SkyLands.WORLD_SKYPVP, 135.5, 71, 8.5);

    public SelectZoneContainerTemplate(ContainerManager containerManager) {
        super(containerManager);

        ItemStack Zone1 = new ItemBuilder(Material.SLIME_BALL).name("§2Zone 1").modifyLore().add("§7Gegen den §aSlimekönig").finish().build();
        ItemStack Zone2 = new ItemBuilder(Material.BARRIER).glow().name("§eZone 2").modifyLore().add("§7Bald verfügbar").finish().build();
        ItemStack Zone3 = new ItemBuilder(Material.BARRIER).glow().name("§cZone 3").modifyLore().add("§7Bald verfügbar").finish().build();

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lOutlands §0> §0§lZonen").setStorageLevel(ContainerStorageLevel.NEW);

        builder.addAction(10, Zone1,
                player -> {
                    player.teleport(zone1);
                    player.sendMessage(Messages.PREFIX_LONG);
                    player.sendMessage(Messages.PREFIX + "§7Willkommen in §aZone 1§7!");
                    player.sendMessage(" ");
                    player.sendMessage(Messages.PREFIX + "In den §ePvE-Zonen §7kannst Du dich im Kampf gegen Monster beweisen.");
                    player.sendMessage(Messages.PREFIX + "Sammle §e25 Monstermarken§7 von jeder Insel, um");
                    player.sendMessage(Messages.PREFIX + "eine §ePassiererlaubnis§7 zu erhalten und zum");
                    player.sendMessage(Messages.PREFIX + "§eBosskampf§7 zugelassen zu werden. §eViel Erfolg!");
                    player.sendMessage(Messages.PREFIX_LONG);
                });
        builder.addAction(13, Zone2, ContainerAction.NONE);
        builder.addAction(16, Zone3, ContainerAction.NONE);

        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }
    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }
}
