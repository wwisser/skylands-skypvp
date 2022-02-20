package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.pve.Helper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandDenyOffer extends AbstractCommand {

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);

        if(Helper.getWitchCacheStatus(player.getName()) == 2 || Helper.getWitchCacheStatus(player.getName()) == 3) {
            player.sendMessage(Messages.PREFIX + "Du hast §eMelisandres Angebot§7 §7abgelehnt§7.");
            Helper.setWitchCacheData(player.getName(), -1);
        } else {
            player.sendMessage(Messages.PREFIX + "§eMelisandre§7 hat dir §ekein§7 Angebot unterbreitet.");
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "denyoffer";
    }
}