package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import me.skylands.skypvp.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandStats extends AbstractCommand {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");

    private UserService userService = SkyLands.userService;

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        User user = args.length > 0 ? this.userService.getUserByName(args[0]) : this.userService.getUser(player);

        if (user == null) {
            player.sendMessage(Messages.PREFIX + "§cDer Spieler '" + args[0] + "' konnte nicht gefunden werden.");
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(user.getName());


        player.sendMessage(Messages.PREFIX + "Stats von §e" + user.getName());
        player.sendMessage(" §7Kills: §e" + user.getKills());
        player.sendMessage(" §7Tode: §e" + user.getDeaths());
        player.sendMessage(" §7Killstreak: §e" + user.getCurrentKillstreak());
        player.sendMessage(" §7KD/r: §e" + user.getKdr());
        player.sendMessage(" §7Level: §e" + ((targetPlayer != null && targetPlayer.isOnline()) ? targetPlayer.getLevel() : user.getLevel()));
        player.sendMessage(" §7Spielzeit: §e" + (user.getPlaytime() / 60) + "h");
        player.sendMessage(" §7Votes: §e" + user.getVotes());
        player.sendMessage(" §7Registriert seit: §e" + DATE_FORMAT.format(new Date(user.getFirstSeen())));

        if (player.isOp()) {
            String lastSeen;

            if (targetPlayer != null && targetPlayer.isOnline()) {
                lastSeen = "Jetzt";
            } else {
                long diff = System.currentTimeMillis() - user.getLastSeen();
                lastSeen = Formatter.INSTANCE.formatMillis(diff);
            }

            player.sendMessage(" §7Zul. gesehen: §e" + lastSeen);
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "stats";
    }

}
