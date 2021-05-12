package me.skylands.skypvp.container.action.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BPPurchaseContainerAction implements ContainerAction {

    @NonNull
    private Consumer<Player> buyAction;
    @NonNull
    private int costs;
    @NonNull
    private boolean closeInventory;

    @Override
    public void process(Player player) {

        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        if (user.getBloodpoints() >= this.costs) {
            user.setBloodpoints(user.getBloodpoints() - this.costs);
            this.buyAction.accept(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        } else {
            player.sendMessage(
                    Messages.PREFIX
                            + "§cDir fehlen noch "
                            + (this.costs - user.getBloodpoints())
                            + " Blutpunkte."
            );
            player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
        }

        if (this.closeInventory) {
            player.closeInventory();
        }
    }
}