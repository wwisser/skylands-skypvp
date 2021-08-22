package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.pve.BossTracker;
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
        this.selectedPlayer = selectRandomPlayerNearby();
        this.bossID = bossTracker.getIDbyUUID(boss.getUniqueId().toString());
    }

    @Override
    public void run() {

        // TODO ADD PERMANENT DISTANCE CHECK = IF DISTANCE BETWEEN TOTEM AND BOSS >= 30 -> TP BOSS TO TOTEM LOC - TBC

        Bukkit.getLogger().info("In: BossAttackTask Time: " + this.timePassed);
        if(this.selectedPlayer != null) {
            Bukkit.getLogger().info("In: BossAttackTask Selected Player: " + this.selectedPlayer.getName());
        } else {
            Bukkit.getLogger().info("In: BossAttackTask Selected Player: " + "NONE");
        }

        if(
                selectedPlayer != null
                && selectedPlayer.isOnline()
                && selectedPlayer.getLocation().distance(this.boss.getLocation()) <= 8
        ) {
            switch (timePassed) {
                case 60:
                    bossAttack.preAttack(selectedPlayer, this.boss);
                    break;
                case 80:
                    bossAttack.attack(selectedPlayer, this.boss);
                    break;
                case 100:
                    bossAttack.postAttack(selectedPlayer, this.boss);
                    this.timePassed = 0;
            }
        } else {
            Bukkit.getLogger().info("Conditions: FALSE -> select new player and reset time (pre)");
            this.selectedPlayer = selectRandomPlayerNearby();
            this.timePassed = 0;
        }

        if(this.boss.getLocation().distance(bossTracker.getBossLocation(bossID)) >= 30) {
            this.boss.teleport(bossTracker.getBossLocation(this.bossID));
        }

        if(!this.bossTracker.isBossAlive(this.bossID)) {
            Bukkit.getLogger().info("Boss died! Canceling Task (pre)");
            Bukkit.getScheduler().cancelTask(this.getTaskId());
        }

        this.timePassed = this.timePassed + 1;
    }

    public Player selectRandomPlayerNearby() {

        List<Entity> nearbyEntites = this.boss.getNearbyEntities(8, 8, 8);

        for (Entity entry : nearbyEntites) {
            if (entry instanceof Player) {
                return (Player) entry;
            }
        }
        return null;
    }
}
