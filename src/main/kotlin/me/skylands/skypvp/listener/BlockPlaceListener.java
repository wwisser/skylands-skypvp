package me.skylands.skypvp.listener;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        if(world == SkyLands.WORLD_SKYBLOCK && !event.isCancelled()) {
            user.setBlocksPlaced(user.getBlocksPlaced() + 1);

            switch (user.getBlocksPlaced()) {
                case 500:
                    player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eBaumeister I§7' erfolgreich abgeschlossen und §c10 Blutpunkte §7erhalten.");
                    user.setBloodPoints(user.getBloodPoints() + 10);
                    break;
                case 1000:
                    player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eBaumeister II§7' erfolgreich abgeschlossen und §c50 Blutpunkte §7erhalten.");
                    user.setBloodPoints(user.getBloodPoints() + 50);
                    break;
                case 10000:
                    player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eBaumeister III§7' erfolgreich abgeschlossen und §c100 Blutpunkte §7erhalten.");
                    user.setBloodPoints(user.getBloodPoints() + 100);
            }
        }
    }
}
