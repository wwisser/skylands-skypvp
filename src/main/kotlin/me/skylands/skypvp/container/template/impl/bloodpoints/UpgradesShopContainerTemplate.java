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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradesShopContainerTemplate extends ContainerTemplate {

    private final ShopContainerTemplate shopContainerTemplate;

    public UpgradesShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);

        this.shopContainerTemplate = shopContainerTemplate;
    }

    @Override
    public void openContainer(Player player) {
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lUpgrades")
                .setStorageLevel(ContainerStorageLevel.NEW)
                .addAction(26, ShopContainerTemplate.ITEM_BACK, shopContainerTemplate::openContainer);

        String reducedEnchantingCostsStatus = user.getHasReducedEnchantingCostsUpgrade() ? "§aBereits freigeschaltet" : "§7Preis: §c25 Blutpunkte";
        ItemStack ReducedXPCosts = new ItemBuilder(Material.EXP_BOTTLE).name("§eReduzierte Verzauberungskosten").modifyLore().add(" ").add("§7Verzauberungskosten sind für").add("dich dauerhaft um §e15%§7 reduziert").add(" ").add(reducedEnchantingCostsStatus).finish().build();

        String damageReductionLevelStatus = user.getDamageReductionLevel() > 0 ? user.getDamageReductionLevel() > 1 ? user.getDamageReductionLevel() > 2 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7-> §eStufe 2 §7<-" : "§7-> §eStufe 1 §7<-" : "§7-> §eNicht freigeschaltet §7<-";
        String damageReductionPriceInfo = user.getDamageReductionLevel() > 0 ? user.getDamageReductionLevel() > 1 ? user.getDamageReductionLevel() > 2 ? " " : "§7Stufe §e3§7: §c100 Blutpunkte" : "§7Stufe §e2§7: §c50 Blutpunkte" : "§7Stufe §e1: §c25 Blutpunkte";
        int damageReductionUpgradePrice = user.getDamageReductionLevel() > 0 ? user.getDamageReductionLevel() > 1 ? 100 : 50 : 25;
        ItemStack DamageReductionUpgrade = new ItemBuilder(Material.PAPER).name("§eReduzierter Schaden").modifyLore().add("").add("§7Auf Stufe §e1§7: -§e3§7% Schaden").add("§7Auf Stufe §e2§7: -§e6§7% Schaden").add("§7Auf Stufe §e3§7: -§e10§7% Schaden").add(" ").add(damageReductionLevelStatus).add(" ").add(damageReductionPriceInfo).finish().glow().build();

        String increasedMobDamageLevelStatus = user.getIncreasedMobDamageLevel() > 0 ? user.getIncreasedMobDamageLevel() > 1 ? user.getIncreasedMobDamageLevel() > 2 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7-> §eStufe 2 §7<-" : "§7-> §eStufe 1 §7<-" : "§7-> §eNicht freigeschaltet §7<-";
        String increasedMobDamagePriceInfo = user.getIncreasedMobDamageLevel() > 0 ? user.getIncreasedMobDamageLevel() > 1 ? user.getIncreasedMobDamageLevel() > 2 ? " " : "§7Stufe §e3§7: §c50 Blutpunkte" : "§7Stufe §e2§7: §c35 Blutpunkte" : "§7Stufe §e1: §c20 Blutpunkte";
        int increasedMobDamageUpgradePrice = user.getIncreasedMobDamageLevel() > 0 ? user.getIncreasedMobDamageLevel() > 1 ? 50 : 35 : 20;
        ItemStack IncreasedMobDamage = new ItemBuilder(Material.DIAMOND_AXE).name("§eMehr Schaden gegen Monster").modifyLore().add("").add("§7Auf Stufe §e1§7: +§e10§7% Schaden").add("§7Auf Stufe §e2§7: +§e20§7% Schaden").add("§7Auf Stufe §e3§7: +§e30§7% Schaden").add(" ").add(increasedMobDamageLevelStatus).add(" ").add(increasedMobDamagePriceInfo).finish().build();

        ItemMeta itemMeta = IncreasedMobDamage.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        IncreasedMobDamage.setItemMeta(itemMeta);

        builder.addAction(10, ReducedXPCosts, (user.getHasReducedEnchantingCostsUpgrade() ? ContainerAction.NONE : new BPPurchaseContainerAction(clicker -> {
            user.setHasReducedEnchantingCostsUpgrade(true);
            player.sendMessage(Messages.PREFIX + "Du hast soeben §eReduzierte Verzauberungskosten§7 freigeschaltet. Deine Verzauberungskosten sind nun dauerhaft um §e15%§7 reduziert.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, 25, true)));

        builder.addAction(13, DamageReductionUpgrade, (user.getDamageReductionLevel() < 3 ? new BPPurchaseContainerAction(clicker -> {
            user.setDamageReductionLevel(user.getDamageReductionLevel() + 1);
            player.sendMessage(Messages.PREFIX + "Du hast soeben §eSchadensreduzierung§7 auf §eStufe " + user.getDamageReductionLevel() + " §7freigeschaltet. Du erhältst nun weniger Schaden.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, damageReductionUpgradePrice, true) : ContainerAction.NONE));

        builder.addAction(16, IncreasedMobDamage, (user.getIncreasedMobDamageLevel() < 3 ? new BPPurchaseContainerAction(clicker -> {
            user.setIncreasedMobDamageLevel(user.getIncreasedMobDamageLevel() + 1);
            player.sendMessage(Messages.PREFIX + "Du hast soeben §eMehr Schaden gegen Monster§7 auf §eStufe " + user.getIncreasedMobDamageLevel() + " §7freigeschaltet. Deine nächste Monsterjagd endet nun garantiert tödlich.");
            clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        }, increasedMobDamageUpgradePrice, true) : ContainerAction.NONE));


        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }
}
