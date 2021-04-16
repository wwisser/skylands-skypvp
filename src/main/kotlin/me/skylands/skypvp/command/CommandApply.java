package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.command.exception.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandApply extends AbstractCommand {
    String applyURL = "https://apply.skylands.me/";
    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        player.sendMessage(Messages.PREFIX + "Bewirb dich jetzt unter: §e" + applyURL);
    }

    @NotNull
    @Override
    public String getName() {
        return "apply";
    }
}
