package me.skylands.skypvp.container.action.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.container.action.ContainerAction;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class PurchaseContainerAction implements ContainerAction {

    @NonNull
    private Consumer<Player> buyAction;
    @NonNull
    private int costs;
    @NonNull
    private boolean closeInventory;

    @Override
    public void process(Player player) {
        if (player.getLevel() >= this.costs) {
            player.setLevel(player.getLevel() - this.costs);
            this.buyAction.accept(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        } else {
            player.sendMessage(
                Messages.PREFIX
                    + "§cDir fehlen noch "
                    + (this.costs - player.getLevel())
                    + " Level."
            );
            player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
        }

        if (this.closeInventory) {
            player.closeInventory();
        }
    }

}
