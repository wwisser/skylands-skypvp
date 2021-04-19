package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.command.exception.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class CommandHelp extends AbstractCommand {

    public static final List<String> CMDS = Arrays.asList(
            "stats", "Spielerstatistiken",  "clan", "Clans",  "vote", "Voten", "level", "Levelsystem",  "is", "Skyblock",  "friede", "Schließe Frieden",  "report", "Spieler melden" ,"kit", "Erhalte dein Kit",  "discord", "Unser Discord-Server",  "ping", "Ping"
    );

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        int page = 1;
        try { page = Integer.parseInt(args.length == 0 ? "1" : args[0]); } catch (NumberFormatException ignored) {}
        if (page*10 > CMDS.size() || page <= 0) { page = 1; }
        player.sendMessage(Messages.PREFIX_LONG);
        player.sendMessage(Messages.PREFIX + "§eHilfe Seite " + page);
        for (int i=(page*10-10); i<(page*10); i+=2) {
            player.sendMessage(Messages.PREFIX + "§e/" + CMDS.get(i) + " §8- §7" + CMDS.get(i+1));
        }
        player.sendMessage(Messages.PREFIX_LONG);
    }

    @NotNull
    @Override
    public String getName() {
        return "help";
    }
}
