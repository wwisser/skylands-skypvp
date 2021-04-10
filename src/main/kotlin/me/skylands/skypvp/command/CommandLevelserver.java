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
                sender.sendMessage(
                        Messages.PREFIX
                                + "§e"
                                + user.getName()
                                + " §7wurden §c"
                                + amount
                                + " §aLevel §7abgezogen."
                );
                break;
            case "set":
                sender.sendMessage(
                        Messages.PREFIX
                                + "§e"
                                + user.getName()
                                + " §7wurden die Level auf §e" + amount + " §7gesetzt."
                );
                break;
        }

        val player = Bukkit.getPlayer(user.getName());
        TransactionType type = TransactionType.valueOf(args[0].toUpperCase());
        if (player != null) {
            player.setLevel(this.getNewLevel(player.getLevel(), amount, type));
        } else {
            user.setLevel(this.getNewLevel(user.getLevel(), amount, type));
            userService.saveUser(user);
        }
    }

    private int getNewLevel(final int current, final int modify, final TransactionType type) {
        switch (type) {
            case ADD:
                return current + modify;
            case SET:
                return modify;
            case REMOVE:
                return Math.max(0, current - modify);
            default:
                throw new RuntimeException("Transaction type unknown");
        }
    }

    enum TransactionType {
        ADD,
        REMOVE,
        SET
    }

    @NotNull
    @Override
    public String getName() {
        return "levelserver";
    }
}
