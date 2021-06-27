package me.skylands.skypvp.container.template.impl.bloodpoints;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectsMenuShopContainerTemplate extends ContainerTemplate {

    private final ShopContainerTemplate shopContainerTemplate;

    public EffectsMenuShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);
        this.shopContainerTemplate = shopContainerTemplate;
    }

    @Override
    public void openContainer(Player player) {
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        String speedDesc = player.hasPotionEffect(PotionEffectType.SPEED) ? "§aKlicke zum Deaktivieren" : "§cNicht aktiviert";
        ItemStack Speed = new ItemBuilder(Material.CHAINMAIL_HELMET).name("§eGeschwindigkeitseffekt").modifyLore().add(" ").add(speedDesc).finish().build();

        String hasteDesc = player.hasPotionEffect(PotionEffectType.FAST_DIGGING) ? "§aKlicke zum Deaktivieren" : "§cNicht aktiviert";
        ItemStack Haste = new ItemBuilder(Material.GOLD_PICKAXE).name("§eEile-Effekt").modifyLore().add(" ").add(hasteDesc).finish().build();

        String waterBreathingDesc = player.hasPotionEffect(PotionEffectType.WATER_BREATHING) ? "§aKlicke zum Deaktivieren" : "§cNicht aktiviert";
        ItemStack WaterBreathing = new ItemBuilder(Material.RAW_FISH).name("§eUnterwasseratmungseffekt").modifyLore().add(" ").add(waterBreathingDesc).finish().build();


        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lInseleffekte")
                .setStorageLevel(ContainerStorageLevel.NEW);

        builder.addAction(10, Speed, player.hasPotionEffect(PotionEffectType.SPEED) ? p -> {
            p.removePotionEffect(PotionEffectType.SPEED);
            p.closeInventory();
            p.sendMessage(Messages.PREFIX + "§eEffekt§7 entfernt.");
        } : user.getHasIslandEffectSpeed() ? p -> {
            if (p.getWorld() == SkyLands.WORLD_SKYBLOCK) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
                p.closeInventory();
                p.sendMessage(Messages.PREFIX + "§eEffekt§7 hinzugefügt.");
            }
        } : ContainerAction.NONE);

        builder.addAction(13, Haste, player.hasPotionEffect(PotionEffectType.FAST_DIGGING) ? p -> {
            p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            p.closeInventory();
            p.sendMessage(Messages.PREFIX + "§eEffekt§7 entfernt.");
        } : user.getHasIslandEffectHaste() ? p -> {
            if (p.getWorld() == SkyLands.WORLD_SKYBLOCK) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
                p.closeInventory();
                p.sendMessage(Messages.PREFIX + "§eEffekt§7 hinzugefügt.");
            }
        } : ContainerAction.NONE);

        builder.addAction(16, WaterBreathing, player.hasPotionEffect(PotionEffectType.WATER_BREATHING) ? p -> {
            p.removePotionEffect(PotionEffectType.WATER_BREATHING);
            p.closeInventory();
            p.sendMessage(Messages.PREFIX + "§eEffekt§7 entfernt.");
        } : user.getHasIslandEffectWaterBreathing() ? p -> {
            if (p.getWorld() == SkyLands.WORLD_SKYBLOCK) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1, true, false));
                p.closeInventory();
                p.sendMessage(Messages.PREFIX + "§eEffekt§7 hinzugefügt.");
            }
        }: ContainerAction.NONE);

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }
}
