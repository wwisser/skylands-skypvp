package me.skylands.skypvp.container.template;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.container.ContainerManager;
import org.bukkit.entity.Player;

@AllArgsConstructor
public abstract class ContainerTemplate {

    protected ContainerManager containerService;

    protected abstract void openContainer(Player player);

    public void open(final Player player) {
        player.closeInventory();
        this.openContainer(player);
    }

}
