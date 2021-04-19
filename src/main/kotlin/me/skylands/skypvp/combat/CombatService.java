package me.skylands.skypvp.combat;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CombatService {

    private static final long COMBAT_TIME = TimeUnit.SECONDS.toMillis(8);
    private static final String[] ALLOWED_COMMANDS = {
            "/msg", "/report", "/vote", "/buy", "/teamchat", "/tc",
            "/spec", "/spectator", "/stats", "/friede", "/frieden", "/stack", "/kit"
    };
    private static final String[] INSTANT_TELEPORT_COMMANDS = {
            "/is", "/tpa", "/tpaccept", "/tpahere", "/top", "/essentials:top", "/askyblock:is", "/essentials:tpaccept", "/is top", "/askyblock:is top"
    };

    private static Map<Player, Long> fightTimestamps = new HashMap<>();

    public static void setFighting(Player player) {
        if (player.hasPermission("skylands.combatlog.bypass")) {
            return;
        }

        if (!isFighting(player)) {
            player.sendMessage(Messages.PREFIX + "§cDu bist jetzt im Kampf, bitte logge dich §c§nnicht§c aus.");
            player.setFlying(false);
            player.setAllowFlight(false);
        }
        fightTimestamps.put(player, System.currentTimeMillis() + COMBAT_TIME);
    }

    public static void detachFight(Player player, boolean intentional) {
        if (!fightTimestamps.containsKey(player)) {
            return;
        }

        fightTimestamps.remove(player);

        if (intentional) {
            player.setHealth(0);
            player.teleport(SkyLands.LOCATION_SPAWN);
        }
    }

    public static boolean isFighting(Player player) {
        return getPlayersInFight().contains(player) && isUpdated(fightTimestamps.get(player));
    }

    public static boolean isUpdated(long timeStamp) {
        return timeStamp > System.currentTimeMillis();
    }

    public static boolean isCommandAllowed(String command) {
        return Arrays
                .stream(ALLOWED_COMMANDS)
                .anyMatch(allowedCommand -> allowedCommand.startsWith(command.toLowerCase()));
    }

    public static boolean isCommandTeleportable(String command) {
        System.out.println(command);
        return Arrays
                .stream(INSTANT_TELEPORT_COMMANDS)
                .anyMatch(allowedCommand -> allowedCommand.startsWith(command.toLowerCase()));
    }

    public static long getStartTimeStamp(Player player) {
        return fightTimestamps.get(player);
    }

    public static List<Player> getPlayersInFight() {
        return fightTimestamps.keySet()
                .stream()
                .filter(OfflinePlayer::isOnline)
                .collect(Collectors.toList());
    }

}
