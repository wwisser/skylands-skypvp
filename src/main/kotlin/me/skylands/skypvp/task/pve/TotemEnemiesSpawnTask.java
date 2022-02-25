package me.skylands.skypvp.task.pve;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.command.CommandPve;
import me.skylands.skypvp.config.TotemConfig;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.Totem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Arrays;
import java.util.List;

public class TotemEnemiesSpawnTask extends BukkitRunnable {

    private final BossTracker bossTracker = new BossTracker();
    private final TotemConfig totemConfig = SkyLands.totemConfig;

    private int timeLeft = 1800;

    private final List<String> holoContent = Arrays.asList("§c§lZone 1", "§2§l⚔ §r§2Slimekönig §2§l⚔", " ", "[TIME]");


    @Override
    public void run() {
        if(!Helper.getTotemsToggled()) return;

        if(Helper.getDebugMode()) Bukkit.getLogger().info("In: TotemEnemiesSpawnTask Time: " + this.timeLeft);

        if (this.timeLeft % 30 == 0) {
            this.spawnTotemMobs();
        }

        if (this.timeLeft == 0) {
            this.spawnBoss();
            this.timeLeft = 1800;
        }

        this.updateHolos(returnTimeUntilBossSpawn());

        this.timeLeft = this.timeLeft - 1;
    }

    private String returnTimeUntilBossSpawn() {
        int minutesLeft = this.timeLeft / 60;
        String secondsLeft = this.timeLeft % 60 < 10 ? "0" + this.timeLeft % 60 : String.valueOf(this.timeLeft % 60);

        return "§e" + minutesLeft + "§7:§e" + secondsLeft;
    }

    private void spawnTotemMobs() {
        for (Totem totem : totemConfig.getTotems().values()) {
            totem.spawnMobs();
        }
    }

    private void spawnBoss() {
        Helper helper = new Helper();

        for (Totem totem : totemConfig.getTotems().values()) {
            if (!this.bossTracker.isBossAlive(totem.getTotemIdentifier())) {
                totem.spawnBoss();

                Location centerLoc = totem.getTotemCenterLocation();
                Location holoLoc = new Location(SkyLands.WORLD_SKYPVP, centerLoc.getX(), centerLoc.getY() + 3, centerLoc.getZ());

                if (!(helper.getHologramAt(holoLoc) == null)) {
                    Hologram holo = helper.getHologramAt(holoLoc);
                    holo.delete();
                }
            }
        }
    }


    private void updateHolos(String timeLeft) {
        Helper helper = new Helper();

        for (Totem totem : totemConfig.getTotems().values()) {
            if (!(totem.getBossType().equals("None"))) {
                Location centerLoc = totem.getTotemCenterLocation();
                Location holoLoc = new Location(SkyLands.WORLD_SKYPVP, centerLoc.getX(), centerLoc.getY() + 3, centerLoc.getZ());

                if (!(helper.getHologramAt(holoLoc) == null)) {
                    Hologram holo = helper.getHologramAt(holoLoc);

                    for(int i = 0; i <= holoContent.size() -1; i++) {
                        try {
                            HologramLine line = holo.getLine(i);
                            if(line instanceof TextLine) {
                                ((TextLine) line).setText(holoContent.get(i).replace("[TIME]", timeLeft));
                            }
                        } catch (IndexOutOfBoundsException exception) {
                            holo.appendTextLine(holoContent.get(i).replace("[TIME]", timeLeft));
                        }
                    }
                }
            }
        }
    }
}
