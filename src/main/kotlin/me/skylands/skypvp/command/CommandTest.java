package me.skylands.skypvp.command;

import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.pve.bosses.BossSlime;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTest extends AbstractCommand {

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        BossSlime bossSlime = new BossSlime(player.getLocation());

        bossSlime.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }

    @NotNull
    @Override
    public String getName() {
        return "test";
    }
}

