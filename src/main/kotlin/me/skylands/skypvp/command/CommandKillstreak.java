package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandKillstreak extends AbstractCommand {

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        player.sendMessage(Messages.PREFIX + "Deine zurzeitige Killstreak: §e" + user.getCurrentKillstreak());
        player.sendMessage(Messages.PREFIX + "Deine Blutpunkte: §e" + user.getBloodpoints());
    }

    @NotNull
    @Override
    public String getName() {
        return "killstreak";
    }
}
