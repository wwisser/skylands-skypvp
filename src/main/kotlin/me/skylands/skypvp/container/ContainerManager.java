package me.skylands.skypvp.container;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.legacy.InventoryKit;
import me.skylands.skypvp.container.legacy.InventoryTrade;
import me.skylands.skypvp.container.listener.InventoryClickListener;
import me.skylands.skypvp.container.listener.InventoryCloseListener;
import org.bukkit.plugin.PluginManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContainerManager {

    private Set<Container> containers = new HashSet<>();

    public ContainerManager() {
        PluginManager pluginManager = SkyLands.plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(this), SkyLands.plugin);
        pluginManager.registerEvents(new InventoryCloseListener(this), SkyLands.plugin);
        pluginManager.registerEvents(new InventoryKit(), SkyLands.plugin);
        pluginManager.registerEvents(new InventoryTrade(), SkyLands.plugin);
    }

    public void destroyContainer(final Container container) {
        this.containers.remove(container);
    }

    public void registerContainer(final Container container) {
        this.containers.add(container);
    }

    public Set<Container> getContainers() {
        return Collections.unmodifiableSet(this.containers);
    }

}
