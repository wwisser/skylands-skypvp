package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.Permissions;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.delay.DelayConfig;
import me.skylands.skypvp.delay.DelayService;
import me.skylands.skypvp.util.TextComponentUtils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandReport extends AbstractCommand implements TabCompleter {

    private static final DelayConfig DELAY_CONFIGURATION = new DelayConfig(
            "Bitte warte noch %time, um erneut einen Spieler zu melden.",
            60000
    );
    private static final List<String> REPORT_REASONS = Arrays.asList(
            "HACKING", "SKIN", "BUGUSING", "LAGVERURSACHUNG"
    );
    private static final String USAGE = "/report <target> <" + String.join("|", REPORT_REASONS) + ">";

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        ValidateCommand.INSTANCE.minArgs(2, args, USAGE);
        Player target = ValidateCommand.INSTANCE.target(args[0], player);

        if (target.hasPermission(Permissions.TEAM)) {
            throw new CommandException("§cDu darfst keine Teammitglieder melden.");
        }

        String reason = args[1].toUpperCase();

        if (REPORT_REASONS
                .stream()
                .noneMatch(validReason -> validReason.equals(reason))) {
            player.sendMessage(Messages.PREFIX + USAGE);
            return;
        }

        DelayService.INSTANCE.handleDelay(player, DELAY_CONFIGURATION, reporter -> {
            String message = Messages.PREFIX
                    + "§4"
                    + target.getName()
                    + " §c("
                    + reason
                    + ") §4von "
                    + reporter.getName();
            String hoverText = "§bKlicke, um Vanish zu aktivieren und dich zum Spieler zu teleportieren";
            String command = "/spec " + target.getName();
            TextComponent textComponent = TextComponentUtils.createClickableComponent(message, hoverText, command);

            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(onlinePlayer -> onlinePlayer.hasPermission("soulhive.report.see"))
                    .forEach(teamMember -> teamMember.spigot().sendMessage(textComponent));

            player.sendMessage(
                    Messages.PREFIX + "Du hast §e" + target.getName() + " §7für §e" + reason + " §7gemeldet."
            );
            player.sendMessage(
                    Messages.PREFIX + "Vielen Dank, wir werden uns schnellstmöglich darum kümmern."
            );
        });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            if (args.length > 2) {
                return Collections.emptyList();
            }
            return REPORT_REASONS;
        } else {
            return Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(player -> !player.hasPermission(Permissions.TEAM) && player != sender)
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "report";
    }

}
