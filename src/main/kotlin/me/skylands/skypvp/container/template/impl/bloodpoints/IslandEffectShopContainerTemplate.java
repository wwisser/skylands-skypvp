package me.skylands.skypvp.container.template.impl.bloodpoints;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.action.impl.BPPurchaseContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IslandEffectShopContainerTemplate extends ContainerTemplate {

    private final ShopContainerTemplate shopContainerTemplate;

    public IslandEffectShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);

        this.shopContainerTemplate = shopContainerTemplate;
    }

    @Override
    public void openContainer(Player player) {
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lInseleffekte")
                .setStorageLevel(ContainerStorageLevel.NEW)
                .addAction(26, ShopContainerTemplate.ITEM_BACK, shopContainerTemplate::openContainer);

        String hasHaste = user.getHasIslandEffectHaste() ? "§aBereits gekauft" : "§7Preis: §c50 Blutpunkte";
        ItemStack Haste = new ItemBuilder(Material.GOLD_PICKAXE).name("§eEile").modifyLore().add("§7Permanter §eInseleffekt").add(" ").add("§eBesucher§7 deiner Insel und §edu").add("§7erhalten den gekauften Effekt.").add("§7Darüber hinaus kannst §edu§7 deine").add("§7Inseleffekte auch auf §efremden Inseln").add("§7ohne eigene Inseleffekte benutzen").add(" ").add(hasHaste).finish().glow().build();

        String hasSpeed = user.getHasIslandEffectSpeed() ? "§aBereits gekauft" : "§7Preis: §c50 Blutpunkte";
        ItemStack Speed = new ItemBuilder(Material.CHAINMAIL_BOOTS).name("§eGeschwindigkeit").modifyLore().add("§7Permanter §eInseleffekt").add(" ").add("§eBesucher§7 deiner Insel und §edu").add("§7erhalten den gekauften Effekt.").add("§7Darüber hinaus kannst §edu§7 deine").add("§7Inseleffekte auch auf §efremden Inseln").add("§7ohne eigene Inseleffekte benutzen").add(" ").add(hasSpeed).finish().glow().build();

        String hasWaterBreathing = user.getHasIslandEffectWaterBreathing() ? "§aBereits gekauft" : "§7Preis: §c50 Blutpunkte";
        ItemStack WaterBreathing = new ItemBuilder(Material.RAW_FISH).name("§eUnterwasseratumung").modifyLore().add("§7Permanter §eInseleffekt").add(" ").add("§eBesucher§7 deiner Insel und §edu").add("§7erhalten den gekauften Effekt.").add("§7Darüber hinaus kannst §edu§7 deine").add("§7Inseleffekte auch auf §efremden Inseln").add("§7ohne eigene Inseleffekte benutzen").add(" ").add(hasWaterBreathing).finish().build();


        builder.addAction(10, Haste, (user.getHasIslandEffectHaste() ? ContainerAction.NONE : new BPPurchaseContainerAction(clicker -> {
            userService.getUser(clicker).setHasIslandEffectHaste(true);
            clicker.sendMessage(Messages.PREFIX + "Du hast soeben den §eEile-Effekt §7für Inseln erworben. Benutze §e/effekte§7 um deine Insel-Effekte zu verwalten.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 50, true)));

        builder.addAction(13, Speed, (user.getHasIslandEffectSpeed() ? ContainerAction.NONE : new BPPurchaseContainerAction(clicker -> {
            userService.getUser(clicker).setHasIslandEffectSpeed(true);
            clicker.sendMessage(Messages.PREFIX + "Du hast soeben den §eGeschwindigkeits-Effekt §7für Inseln erworben. Benutze §e/effekte§7 um deine Insel-Effekte zu verwalten.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 50, true)));

        builder.addAction(16, WaterBreathing, (user.getHasIslandEffectWaterBreathing() ? ContainerAction.NONE : new BPPurchaseContainerAction(clicker -> {
            userService.getUser(clicker).setHasIslandEffectWaterBreathing(true);
            clicker.sendMessage(Messages.PREFIX + "Du hast soeben den §eUnterwasseratmungs-Effekt §7für deine Inseln erworben. Benutze §e/effekte§7 um deine Insel-Effekte zu verwalten.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 50, true)));

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }
}