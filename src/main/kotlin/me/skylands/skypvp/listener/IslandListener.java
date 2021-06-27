package me.skylands.skypvp.listener;

import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import com.wasteofplastic.askyblock.events.IslandExitEvent;
import com.wasteofplastic.askyblock.events.IslandJoinEvent;
import com.wasteofplastic.askyblock.events.IslandPostLevelEvent;
import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IslandListener implements Listener {

    private final UserService userService = SkyLands.userService;

    @EventHandler
    public void onIslandEnter(IslandEnterEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayer());
        User islandOwner = this.userService.getUserByUuid(event.getIslandOwner().toString());
        User pUser = this.userService.getUser(player);
        int purchasedEffects = 0;

        if (islandOwner.getHasIslandEffectSpeed()) purchasedEffects++;
        if (islandOwner.getHasIslandEffectHaste()) purchasedEffects++;
        if (islandOwner.getHasIslandEffectWaterBreathing()) purchasedEffects++;

        if (purchasedEffects > 0 && !event.getIslandOwner().toString().equals(player.getUniqueId().toString())) {
            player.sendMessage(Messages.PREFIX + "§e" + player.getName() + " §7hat für seine Insel folgende §eEffekte §7freigeschaltet:");
        }

        if (islandOwner.getHasIslandEffectSpeed() || pUser.getHasIslandEffectSpeed()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
            if (islandOwner.getHasIslandEffectSpeed() && !event.getIslandOwner().toString().equals(player.getUniqueId().toString()))
                player.sendMessage("     §7- §eGeschwindigkeit");
        }
        if (islandOwner.getHasIslandEffectHaste() || pUser.getHasIslandEffectHaste()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
            if (islandOwner.getHasIslandEffectHaste() && !event.getIslandOwner().toString().equals(player.getUniqueId().toString()))
                player.sendMessage("     §7- §eEile");
        }
        if (islandOwner.getHasIslandEffectWaterBreathing() || pUser.getHasIslandEffectWaterBreathing()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1, true, false));
            if (islandOwner.getHasIslandEffectWaterBreathing() && !event.getIslandOwner().toString().equals(player.getUniqueId().toString()))
                player.sendMessage("     §7- §eUnterwasseratmung");
        }
    }

    @EventHandler
    public void onIslandExit(IslandExitEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayer());

        if (player.hasPotionEffect(PotionEffectType.SPEED)) {
            player.removePotionEffect(PotionEffectType.SPEED);
        }

        if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }

        if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        }
    }

    @EventHandler
    public void onIslandTeamJoin(IslandJoinEvent event) {
        Player islandOwner = Bukkit.getPlayer(event.getIslandOwner());
        User islandOwnerAsUser = this.userService.getUserByUuid(event.getIslandOwner().toString());

        if (event.getIsland().getMembers().size() >= 3 && !islandOwnerAsUser.getTeamWorkChallengeCompleted()) {
            islandOwnerAsUser.setTeamWorkChallengeCompleted(true);
            islandOwnerAsUser.setBloodPoints(islandOwnerAsUser.getBloodPoints() + 20);

            if (islandOwner != null) islandOwner.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eTeamwork§7' erfolgreich abgeschlossen und §c20 Blutpunkte§7 erhalten.");
        }
    }

    @EventHandler
    public void onIslandLevelUpdate(IslandPostLevelEvent event) {
        Bukkit.getLogger().info("islandpostlevel called");
        Player islandOwner = Bukkit.getPlayer(event.getIslandOwner());
        User islandOwnerAsUser = this.userService.getUserByUuid(event.getIslandOwner().toString());
        long newLevel = event.getLongLevel();
        Bukkit.getLogger().info(Long.toString(newLevel));

        if(newLevel >= 1000 && !islandOwnerAsUser.getTeamWorkChallengeCompleted()) {
            Bukkit.getLogger().info("reqs met!");
            islandOwnerAsUser.setIslandLevelChallengeCompleted(true);
            islandOwnerAsUser.setBloodPoints(islandOwnerAsUser.getBloodPoints() + 250);

            if (islandOwner != null) islandOwner.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eUnglaublich!§7' erfolgreich abgeschlossen und §c250 Blutpunkte§7 erhalten.");
        } else {
            Bukkit.getLogger().info("reqs not met");
        }
    }
}