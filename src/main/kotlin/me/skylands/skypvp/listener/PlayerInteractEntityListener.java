package me.skylands.skypvp.listener;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.legacy.InventoryKit;
import me.skylands.skypvp.container.legacy.InventoryTrade;
import me.skylands.skypvp.container.template.impl.pve.MelisandreContainerTemplate;
import me.skylands.skypvp.container.template.impl.pve.SelectZoneContainerTemplate;
import me.skylands.skypvp.container.template.impl.pve.TradeTokensContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.user.UserService;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEntityListener implements Listener {

    private final SelectZoneContainerTemplate selectZoneContainerTemplate = new SelectZoneContainerTemplate(SkyLands.containerManager);
    private final TradeTokensContainerTemplate tradeTokensContainerTemplate = new TradeTokensContainerTemplate(SkyLands.containerManager);
    private final UserService userService = SkyLands.userService;
    private final String witchPrefix = "§8[§5§lHexe§r§8] §bMelisandre§7: ";
    private final ItemStack catalysator = new ItemBuilder(Material.TRIPWIRE_HOOK).name("§3Katalysator").modifyLore().add(" ").add("§7Seltener §eBraugestand").add("§7Bringe diesen §eKatalysator").add("§7zu §eMelisandre.").finish().glow().build();
    private final MelisandreContainerTemplate melisandreContainerTemplate = new MelisandreContainerTemplate(SkyLands.containerManager);
    private final BaseComponent[] offerMessage = new ComponentBuilder(Messages.PREFIX + "§eAngebot annehmen? ")
            .append("[ANNEHMEN]")
            .bold(true)
            .color(ChatColor.GREEN)
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptoffer"))
            .append(" ")
            .append("[ABLEHNEN]")
            .bold(true)
            .color(ChatColor.RED)
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/denyoffer")).create();


    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        EntityType entityType = event.getRightClicked().getType();
        if (player.getWorld() == SkyLands.WORLD_SKYPVP) {
            if (player.getLocation().getBlockY() > SkyLands.Companion.getSpawnHeight()) {

                if (entityType == EntityType.WITCH) {
                    player.performCommand("levelshop");
                }
                if (entityType == EntityType.ENDERMAN) {
                    player.performCommand("is");
                }
                if (entityType == EntityType.SKELETON) {
                    InventoryKit.open(player);
                }
                if (entityType == EntityType.VILLAGER) {
                    InventoryTrade.open(player);
                }
                if (entityType == EntityType.COW) {
                    player.performCommand("is warps");
                }
                if (entityType == EntityType.ZOMBIE) {
                    selectZoneContainerTemplate.open(player);
                }
            }
            if (entityType == EntityType.VILLAGER) {
                tradeTokensContainerTemplate.open(player);
            }
            if (entityType == EntityType.WITCH) {

                if (userService.getUser(player).getWitchUnlocked()) {
                    melisandreContainerTemplate.openContainer(player);
                    return;
                }

                if (player.getItemInHand().equals(catalysator) && !(Helper.getWitchCacheStatus(player.getName()) == 2)) {
                    Helper.setWitchCacheData(player.getName(), 2);
                    player.sendMessage(witchPrefix + "Du hast den §dSlimekönig§7 also tatsächlich besiegt! Ich wusste, dass ich mich auf Dich verlassen kann. Überlasse mir deinen §dKatalysator§7 und §c20 Blutpunkte§7 und meine Braukünste stehen Dir zur Verfügung.");
                    player.sendMessage(" ");
                    player.spigot().sendMessage(offerMessage);
                } else {
                    if (Helper.getWitchCacheStatus(player.getName()) == 0) {
                        player.sendMessage(witchPrefix + "Bringe mir einen §dKatalysator§7 und ich biete Dir meine Dienste an.");
                        Helper.setWitchCacheData(player.getName(), 1);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyLands.plugin, () -> {
                            if(Helper.getWitchCacheStatus(player.getName()) <= 1) Helper.setWitchCacheData(player.getName(), 0);
                        }, 20 * 60);
                    } else if (Helper.getWitchCacheStatus(player.getName()) == 2) {
                        player.spigot().sendMessage(offerMessage);
                        Helper.setWitchCacheData(player.getName(), 3);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyLands.plugin, () -> {
                            if(Helper.getWitchCacheStatus(player.getName()) == -1) return;
                            Helper.setWitchCacheData(player.getName(), 2);
                        }, 20 * 15);
                    } else {
                        if (Helper.getWitchCacheStatus(player.getName()) == 3 || Helper.getWitchCacheStatus(player.getName()) == 1) return;

                        player.sendMessage(witchPrefix + "Psst, §d" + player.getName() + "§7! Kannst Du mir einen Gefallen tun? Für meine neuartigen Tränke brauche ich dringend einen §dKatalysator§7.. aber gegen den §dSlimekönig§7 komme selbst ich nicht an. Um Deine legendären Kampfkünste jedoch weiß jede Hexe im Dorf. Bitte, §dbesiege den Slimekönig§7 und bringe mir einen §dKatalysator§7. Ich würde Dich reich belohnen..");
                        Helper.setWitchCacheData(player.getName(), 0);
                    }
                }
            }
        }
    }
}
