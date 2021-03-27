package me.skylands.skypvp.command;

import lombok.val;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.command.exception.InvalidArgsException;
import me.skylands.skypvp.command.exception.TargetNotFoundException;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandLevelserver extends AbstractCommand {

    private static final String USAGE = "/levelserver <add|remove|set> <name> <anzahl> [-s]";

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        ValidateCommand.INSTANCE.permission(sender, "skylands.jewelserver");
        ValidateCommand.INSTANCE.minArgs(3, args, USAGE);

        if (!Arrays.asList("add", "remove", "set").contains(args[0].toLowerCase())) {
            throw new InvalidArgsException(USAGE);
        }

        UserService userService = SkyLands.userService;
        final User user = userService.getUserByName(args[1]);
        final int amount = ValidateCommand.INSTANCE.positiveNumber(args[2]);
        boolean silent = args.length > 3 && args[3].equalsIgnoreCase("-s");

        if (user == null) {
            throw new TargetNotFoundException(args[1]);
        }

        switch (args[0].toLowerCase()) {
            case "add":
                user.setLevel(user.getLevel() + amount);

                if (silent) {
                    sender.sendMessage(
                            Messages.PREFIX
                                    + "Dem Spieler §e"
                                    + user.getName()
                                    + " §7wurden §e"
                                    + amount
                                    + " Level §7hinzugefügt."
                    );
                } else {
                    Bukkit.broadcastMessage(
                            Messages.PREFIX
                                    + "§e"
                                    + user.getName()
                                    + " §7wurden §e"
                                    + amount
                                    + " Level §7hinzugefügt."
                    );
                    //Bukkit.broadcastMessage(Messages.PREFIX + "Jetzt auch Level kaufen: §d§l/buy");
                }
                break;
            case "remove":
                user.setLevel(Math.max(0, user.getLevel() - amount));
                sender.sendMessage(
                        Messages.PREFIX
                                + "§e"
                                + user.getName()
                                + " §7wurden §c"
                                + amount
                                + " §dLevel §7abgezogen."
                );
                break;
            case "set":
                user.setLevel(amount);
                sender.sendMessage(
                        Messages.PREFIX
                                + "§e"
                                + user.getName()
                                + " §7wurden die Level auf §e" + amount + " §7gesetzt."
                );
                break;
        }

        userService.saveUser(user);
        val player = Bukkit.getPlayer(user.getName());
        if (player != null) {
            player.setLevel(user.getLevel());
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "levelserver";
    }
}
