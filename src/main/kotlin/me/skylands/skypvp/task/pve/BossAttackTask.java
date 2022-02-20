package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.bosses.attacks.AreaMultiAttack;
import me.skylands.skypvp.pve.bosses.attacks.BossAttack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BossAttackTask extends BukkitRunnable {

    private final CraftEntity boss;
    private final BossAttack bossAttack;
    private final BossTracker bossTracker = new BossTracker();
    private Player selectedPlayer;
    private int bossID;

    private int timePassed = 0;

    public BossAttackTask(CraftEntity boss, BossAttack bossAttack) {
        this.boss = boss;
        this.bossAttack = bossAttack;
        this.selectedPlayer = null;
        this.bossID = bossTracker.getIDbyUUID(boss.getUniqueId().toString());
    }

    public void run() {

        if(Helper.getDebugMode()) Bukkit.getLogger().info("In: BossAttackTask Time: " + this.timePassed);

        if(this.boss.isDead()) {
            this.cancel();
            return;
        }

        if (this.selectedPlayer == null || this.selectedPlayer.getWorld() != SkyLands.WORLD_SKYPVP) {
            this.selectedPlayer = selectRandomPlayerNearby();
        } else {
            if (!(this.boss.getLocation().distance(selectedPlayer.getLocation()) > 12)) {
                switch (this.timePassed) {
                    case 35:
                        this.bossAttack.preAttack(this.selectedPlayer, this.boss);
                        break;
                    case 43:
                        this.bossAttack.attack(this.selectedPlayer, this.boss);
                        break;
                    case 52: // 50
                        this.bossAttack.postAttack(this.selectedPlayer, this.boss);
                }
            } else {
                this.selectedPlayer = selectRandomPlayerNearby();
            }
        }

        if(this.bossAttack instanceof AreaMultiAttack) {
            if (timePassed >= 44 && this.timePassed < 50) {
                this.bossTracker.cancelParticleTask(bossID, false);
            }
        }

        if (this.boss.getLocation().distance(this.bossTracker.getTotemCenterLocation(this.bossID)) >= 30) {
            this.bossTracker.teleportBossToTotem(this.bossID);
        }

        this.timePassed++;

        if (this.timePassed == 51) {
            this.timePassed = 0;
        }
    }

    public Player selectRandomPlayerNearby() {

        List<Entity> nearbyEntites = this.boss.getNearbyEntities(12, 10, 12);

        for (Entity entry : nearbyEntites) {
            if (entry instanceof Player) {
                return (Player) entry;
            }
        }
        return null;
    }
}
