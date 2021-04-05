package me.skylands.skypvp.container.action;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ContainerAction {

    ContainerAction NONE = player -> {};

    void process(Player player);

}
