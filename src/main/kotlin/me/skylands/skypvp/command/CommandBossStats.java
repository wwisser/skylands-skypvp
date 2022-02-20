package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.container.template.impl.pve.StatsContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.data.CacheData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandBossStats extends AbstractCommand {

    private final BossTracker bossTracker = new BossTracker();
    private final Helper helper = new Helper();

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);

        if (helper.getDataStatus() == 1) {
            StatsContainerTemplate statsContainerTemplate = new StatsContainerTemplate(SkyLands.containerManager, helper.getProcessedData());
            statsContainerTemplate.openContainer(player);
        } else {
            player.sendMessage(Messages.PREFIX + "Keine §eStatistiken§7 verfügbar.");
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "bstats";
    }
}