package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandAcceptOffer extends AbstractCommand {

    private final UserService userService = SkyLands.userService;
    private final String witchPrefix = "§8[§5§lHexe§r§8] §bMelisandre§7: ";
    private final ItemStack catalysator = new ItemBuilder(Material.TRIPWIRE_HOOK).name("§3Katalysator").modifyLore().add(" ").add("§7Seltener §eBraugestand").add("§7Bringe diesen §eKatalysator").add("§7zu §eMelisandre.").finish().glow().build();

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);

        if(Helper.getWitchCacheStatus(player.getName()) == 2 || Helper.getWitchCacheStatus(player.getName()) == 3) {
            if(Helper.hasItem(player, catalysator, 1)) {
                if(userService.getUser(player).getBloodPoints() >= 20) {
                    player.getInventory().removeItem(catalysator);
                    userService.getUser(player).setBloodPoints(userService.getUser(player).getBloodPoints() - 20);
                    userService.getUser(player).setWitchUnlocked(true);
                    userService.saveUser(userService.getUser(player));

                    //Helper.setWitchCacheData(player.getName(), -1);
                    player.sendMessage(witchPrefix + "§dVielen Dank! §7Nun helfe ich Dir gerne.");
                } else {
                    player.sendMessage(Messages.PREFIX + "§7Du besitzt nicht genug §cBlutpunkte§7.");
                }
            } else {
                player.sendMessage(Messages.PREFIX + "§7Du besitzt keinen §eKatalysator§7.");
                //Helper.setWitchCacheData(player.getName(), -1);
            }
            Helper.setWitchCacheData(player.getName(), -1);
        } else {
            player.sendMessage(Messages.PREFIX + "§eMelisandre§7 hat dir §ekein§7 Angebot unterbreitet.");
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "acceptoffer";
    }
}
