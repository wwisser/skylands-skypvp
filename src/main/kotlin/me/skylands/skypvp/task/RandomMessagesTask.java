package me.skylands.skypvp.task;

import me.skylands.skypvp.SkyLands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMessagesTask extends BukkitRunnable {

    private final List<String> messages = SkyLands.randomMessages.getAll();

    @Override
    public void run() {
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + returnRandomMessage().replace("\\r", "").replace("\\n", "").replace("\n", "").replace("\r", ""));
        Bukkit.getServer().broadcastMessage(" ");
    }

    private String returnRandomMessage() {
        int randomEntry = ThreadLocalRandom.current().nextInt(0, messages.size());
        return messages.get(randomEntry);
    }
}
