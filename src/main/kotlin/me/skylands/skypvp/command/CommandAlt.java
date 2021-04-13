package me.skylands.skypvp.command;

import lombok.NonNull;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.Permissions;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.ipmatching.IpMatchingService;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandAlt extends AbstractCommand {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.INSTANCE.permission(sender, Permissions.TEAM);
        ValidateCommand.INSTANCE.minArgs(1, args, "/alts <name>");
        final String arg = args[0];

        sender.sendMessage(Messages.PREFIX + "§cAccounts von §a" + arg);

        UserService userService = SkyLands.userService;
        IpMatchingService ipResolverService = SkyLands.ipMatchingService;

        final User target = userService.getUserByName(arg);

        if (target == null) {
            return;
        }

        final String uuid = target.getUuid();
        final Set<String> ips = ipResolverService.getAlternativeIps(uuid);

        final Map<String, Set<String>> uuidPerIp = ips
                .stream()
                .collect(Collectors.toMap(ip -> ip, ipResolverService::getAlternativeAccounts));

        uuidPerIp.forEach((ip, uuidSet) -> {
            final String names = uuidSet
                    .stream()
                    .map(userService.getUserRepository()::fetchNameByUuid)
                    .collect(Collectors.joining(", "));

            sender.sendMessage("  §7- §3" + ip.replace("*", ".") + " §a:: §3" + names);
        });
    }

    @NotNull
    @Override
    public String getName() {
        return "alt";
    }
}
