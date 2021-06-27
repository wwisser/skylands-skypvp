package me.skylands.skypvp.command;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.container.template.impl.bloodpoints.EffectsMenuShopContainerTemplate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandEffects extends AbstractCommand {

    private final EffectsMenuShopContainerTemplate effectsMenuShopContainerTemplate = new EffectsMenuShopContainerTemplate(SkyLands.containerManager, new ShopContainerTemplate(SkyLands.containerManager));
    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);

        this.effectsMenuShopContainerTemplate.openContainer(player);
    }

    @NotNull
    @Override
    public String getName() {
        return "effekte";
    }
}
