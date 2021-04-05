package me.skylands.skypvp.container.listener;

import lombok.AllArgsConstructor;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerEntry;
import me.skylands.skypvp.container.ContainerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private ContainerManager containerService;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();
        final Map<Container, ContainerEntry> elementsToExecute = new HashMap<>(); // stored to prevent ConcurrentModificationException

        for (Container container : this.containerService.getContainers()) {
            if (!inventory.getName().equals(container.getName())) {
                continue;
            }
            event.setCancelled(container.isEventCancelled());

            if (currentItem != null) {
                container.getActions().keySet()
                    .stream()
                    .filter(containerEntry -> containerEntry.getItemStack().equals(currentItem))
                    .forEach(containerEntry -> elementsToExecute.put(container, containerEntry));
            }
        }

        elementsToExecute.entrySet()
            .stream()
            .findFirst()
            .ifPresent(entry -> entry.getKey().getActions().get(entry.getValue()).process(player));
    }

}
