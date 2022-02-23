package me.skylands.skypvp.command;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.container.template.impl.pve.SelectZoneContainerTemplate;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandOutlands extends AbstractCommand {
    private final SelectZoneContainerTemplate selectZoneContainerTemplate = new SelectZoneContainerTemplate(SkyLands.containerManager);

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        selectZoneContainerTemplate.open(ValidateCommand.INSTANCE.onlyPlayer(sender));
    }

    @NotNull
    @Override
    public String getName() {
        return "outlands";
    }
}
