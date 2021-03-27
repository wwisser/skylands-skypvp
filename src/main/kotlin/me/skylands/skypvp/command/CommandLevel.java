package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.command.exception.InvalidArgsException;
import me.skylands.skypvp.command.exception.TargetNotFoundException;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import me.skylands.skypvp.util.TransactionUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandLevel extends AbstractCommand {

    private static final String USAGE = "/level pay <name> <anzahl>";

    private UserService userService = SkyLands.userService;

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);

        if (args.length < 1) {
            final User user = this.userService.getUser(player);

            player.sendMessage(Messages.PREFIX + "§aLevel §7System");
            player.sendMessage(" §7Deine Level: §e" + player.getLevel());
            player.sendMessage(" §7Transferieren: §e/level pay <name> <anzahl>");
            player.sendMessage(" §7Shop: §e/levelshop");
          //  player.sendMessage(" §7Juwelen kaufen: §e/buy");
            return;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("pay")) {
            throw new InvalidArgsException(USAGE);
        }

        final User user = this.userService.getUser(player);
        User targetUser;

        try {
            targetUser = this.userService.getUser(ValidateCommand.INSTANCE.target(args[1], sender));
        } catch (TargetNotFoundException e) {
            final User userByName = this.userService.getUserByName(args[1]);

            if (userByName != null) {
                targetUser = this.userService.getUserByName(args[1]);
            } else {
                throw e;
            }
        }

        final int amount = ValidateCommand.INSTANCE.amount(args[2]);
        final boolean success = TransactionUtils.INSTANCE.handleTransaction(player, targetUser, Bukkit.getPlayer(targetUser.getName()), amount);

        if (success) {
            player.sendMessage(
                    Messages.PREFIX
                            + "Du hast §e"
                            + targetUser.getName()
                            + " §d" + amount + " Level §7überwiesen."
            );

            final Player targetPlayer = Bukkit.getPlayer(targetUser.getName());

            if (targetPlayer != null && targetPlayer.isOnline()) {
                targetPlayer.sendMessage(
                        Messages.PREFIX
                                + "Du hast §d"
                                + amount
                                + " Level §7von §e"
                                + player.getName()
                                + " §7erhalten!"
                );
            }
        } else {
            player.sendMessage(
                    Messages.PREFIX
                            + "Dir fehlen "
                            + (amount - player.getLevel())
                            + " Juwelen, um diese Transaktion durchzuführen."
            );
          //  player.sendMessage(Messages.PREFIX + "Jetzt §a§lLevel §7kaufen §8> §e/buy");
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "level";
    }
    
}
