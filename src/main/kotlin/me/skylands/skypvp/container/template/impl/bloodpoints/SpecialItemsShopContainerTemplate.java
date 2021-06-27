package me.skylands.skypvp.container.template.impl.bloodpoints;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.impl.BPPurchaseContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.item.LightArtifactItemFactory;
import me.skylands.skypvp.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpecialItemsShopContainerTemplate extends ContainerTemplate {

    private final Container container;
    private final ShopContainerTemplate shopContainerTemplate;

    public SpecialItemsShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);
        this.shopContainerTemplate = shopContainerTemplate;

        ItemStack LightArtifactPreview = new ItemBuilder(Material.GHAST_TEAR).name("§fArtefakt des §eLichts").glow().modifyLore().add("§7Ein sagenumwobenes §eLichtartefakt§7!").add("§7Behalte dein Inventar einmalig beim Tod.").add(" ").add("§7Preis: §c50 Blutpunkte").finish().build();
        ItemStack LightArtifact = LightArtifactItemFactory.INSTANCE.createLightArtifactItem();
        ItemStack BeaconPreview = new ItemBuilder(Material.BEACON).name("§bLeuchtfeuer").modifyLore().add("§7Füge deiner Insel §bEffekte§7 hinzu.").add(" ").add("§7Preis: §c30 Blutpunkte").finish().build();
        ItemStack Beacon = new ItemBuilder(Material.BEACON).build();
        ItemStack DragonEggPreview = new ItemBuilder(Material.DRAGON_EGG).name("§5Drachenei").modifyLore().add("§7Verziere deine Insel mit einem §5Drachenei§7!").add(" ").add("§7Preis: §c100 Blutpunkte").finish().build();
        ItemStack DragonEgg = new ItemBuilder(Material.DRAGON_EGG).build();
        ItemStack NetherStarPreview = new ItemBuilder(Material.NETHER_STAR).name("§cNetherstern").modifyLore().add("§7Sähe ein §cNetherstern§7 auf deiner Insel nicht toll aus?").add(" ").add("§7Preis: §c100 Blutpunkte").finish().build();
        ItemStack NetherStar = new ItemBuilder(Material.NETHER_STAR).build();

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lBesondere Items")
                .setStorageLevel(ContainerStorageLevel.NEW)
                .addAction(26, ShopContainerTemplate.ITEM_BACK, shopContainerTemplate::openContainer);

        builder.addAction(12, BeaconPreview, new BPPurchaseContainerAction(clicker -> {
            ItemUtils.INSTANCE.addAndDropRest(clicker, Beacon);
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 30, true));

        builder.addAction(10, LightArtifactPreview, new BPPurchaseContainerAction(clicker -> {
            ItemUtils.INSTANCE.addAndDropRest(clicker, LightArtifact);
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 50, true));

        builder.addAction(14, DragonEggPreview, new BPPurchaseContainerAction(clicker -> {
            ItemUtils.INSTANCE.addAndDropRest(clicker, DragonEgg);
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 100, true));

        builder.addAction(16, NetherStarPreview, new BPPurchaseContainerAction(clicker -> {
            ItemUtils.INSTANCE.addAndDropRest(clicker, NetherStar);
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 100, true));


        final Container builtContainer = builder.build();

        this.container = builtContainer;
        super.containerService.registerContainer(builtContainer);
    }

    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }
}
