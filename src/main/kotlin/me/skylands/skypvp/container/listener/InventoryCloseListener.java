package me.skylands.skypvp.container.listener;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class InventoryCloseListener implements Listener {

    private ContainerManager containerService;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        Container containerToDestroy = null;

        for (Container container : this.containerService.getContainers()) {
            if (inventory.getName().equals(container.getName())) {
                container.getCloseHook().accept(player, event);

                if (container.isDestroy()) {
                    containerToDestroy = container;
                }
            }
        }

        if (containerToDestroy != null) {
            this.containerService.destroyContainer(containerToDestroy);
        }
    }

}
