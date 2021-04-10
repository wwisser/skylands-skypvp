package me.skylands.skypvp.task;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.combat.CombatService;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : CombatService.getPlayersInFight()) {
            if (player.hasPermission("skylands.combatlog.bypass")) {
                continue;
            }

            if (!CombatService.isUpdated(CombatService.getStartTimeStamp(player))) {
                player.sendMessage(Messages.PREFIX + "Du bist nicht mehr im Kampf und darfst dich sicher ausloggen.");
                CombatService.detachFight(player, false);
                continue;
            }

            if (player.isInsideVehicle() && player.getVehicle() instanceof LivingEntity) {
                player.getVehicle().remove();
            }

            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

}
