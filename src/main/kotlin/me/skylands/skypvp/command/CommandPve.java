package me.skylands.skypvp.command;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.Permissions;
import me.skylands.skypvp.command.exception.CommandException;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.util.FontUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandPve extends AbstractCommand {

    private final BossTracker bossTracker = new BossTracker();
    private final ItemStack z1MainIslandTicket = new ItemBuilder(Material.PAPER).glow().name("§eMonstermarke").modifyLore().add("§7Monstermarke der §eHauptinsel").add(" ").add("§7Du benötigst §e25§7 Marken für").add("§7eine §ePassiererlaubnis§7.").finish().amount(25).build();
    private final ItemStack z1PvPIslandTicket = new ItemBuilder(Material.PAPER).glow().name("§eMonstermarke").modifyLore().add("§7Monstermarke der §ePvP-Insel").add(" ").add("§7Du benötigst §e25§7 Marken für").add("§7eine §ePassiererlaubnis§7.").finish().amount(25).build();

    @Override
    public void process(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) throws CommandException {
        Player player = ValidateCommand.INSTANCE.onlyPlayer(sender);
        ValidateCommand.INSTANCE.permission(player, Permissions.ADMIN);

        if (args.length < 2) {

            if (args.length == 0) {
                sendMenu(player);
                return;
            }

            if (args[0].equals("toggle")) {
                Helper.toggleTotems();
                player.sendMessage(Messages.PREFIX + "Totems wurden " + (Helper.getTotemsToggled() ? "§aaktiviert" : "§cdeaktiviert"));
            } else if(args[0].equals("debug")) {
                Helper.toggleDebugMode();
                player.sendMessage(Messages.PREFIX + "Debug-Modus wurde " + (Helper.getDebugMode() ? "§aaktiviert" : "§cdeaktiviert"));
            } else if(args[0].equals("givetokens")) {
                player.getInventory().addItem(z1MainIslandTicket);
                player.getInventory().addItem(z1PvPIslandTicket);
                player.sendMessage(Messages.PREFIX + "2x §e25 Monstermarken§7 erhalten.");
            } else {
                sendMenu(player);
            }
        } else { // Mind. 1 Subcommand
            if (args[0].equals("tp")) {
                try {
                    int bossID = Integer.parseInt(args[1]);
                    if (bossTracker.isValidID(bossID) && bossTracker.isBossAlive(bossID)) {
                        CraftEntity bossHandle = this.bossTracker.getBossHandle(bossID);
                        player.teleport(bossHandle.getLocation());
                        player.sendMessage(Messages.PREFIX + "Teleportiere...");
                    } else {
                        player.sendMessage(Messages.PREFIX + "BossID ungültig oder Boss tot.");
                    }
                } catch (NumberFormatException exception) {
                    sendMenu(player);
                }
            } else if (args[0].equals("kill")) {
                if (args.length > 2) {
                    if (args[2].equals("-s")) {
                        try {
                            int bossID = Integer.parseInt(args[1]);
                            if (bossTracker.isValidID(bossID) && bossTracker.isBossAlive(bossID)) {
                                this.bossTracker.killBoss(bossID, true);
                                player.sendMessage(Messages.PREFIX + "Du hast den Boss mit der ID §e" + bossID + " §7getötet.");
                            } else {
                                player.sendMessage(Messages.PREFIX + "BossID ungültig oder Boss tot.");
                            }
                        } catch (NumberFormatException exception) {
                            sendMenu(player);
                        }
                    }
                } else {
                    try {
                        int bossID = Integer.parseInt(args[1]);
                        if (bossTracker.isValidID(bossID) && bossTracker.isBossAlive(bossID)) {
                            this.bossTracker.killBoss(bossID, false);
                            player.sendMessage(Messages.PREFIX + "Du hast den Boss mit der ID §e" + bossID + " §7getötet.");
                        } else {
                            player.sendMessage(Messages.PREFIX + "BossID ungültig oder Boss tot.");
                        }
                    } catch (NumberFormatException exception) {
                        sendMenu(player);
                    }
                }
            } else {
                sendMenu(player);
            }
        }
    }

    // Info command?
    private void sendMenu(Player player) {
        player.sendMessage(Messages.PREFIX_LONG);
        player.sendMessage("        §ePvE-Verwaltung");
        player.sendMessage("");
        player.sendMessage(Messages.PREFIX + "§e/pve tp <bossID> §8- §7Teleportiert zu Boss");
        player.sendMessage(Messages.PREFIX + "§e/pve kill <bossID> [-s] §8- §7Tötet Boss [leise]");
        player.sendMessage(Messages.PREFIX + "§e/pve givetokens §8- §7Gibt benötigte Monstermarken");
        player.sendMessage(Messages.PREFIX + "§e/pve toggle §8- §7Schaltet Totems ein/aus");
        player.sendMessage(Messages.PREFIX + "§e/pve debug §8- §7Schaltet Debug-Modus ein/aus");
        player.sendMessage(Messages.PREFIX_LONG);
    }

    @NotNull
    @Override
    public String getName() {
        return "pve";
    }
}
