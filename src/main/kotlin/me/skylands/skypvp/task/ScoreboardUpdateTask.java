package me.skylands.skypvp.task;

import lombok.Getter;
import lombok.Setter;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.scoreboard.DynamicScoreboard;
import me.skylands.skypvp.scoreboard.ToplistScoreboard;
import me.skylands.skypvp.scoreboard.UserScoreboard;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardUpdateTask extends BukkitRunnable {

    private Map<Player, DynamicScoreboard> scoreboards = new HashMap<>();

    UserService userService = SkyLands.userService;

    @Getter @Setter
    private static boolean showTopList = false;

    private static Class<? extends DynamicScoreboard> getTypeByState() {
        return showTopList ? ToplistScoreboard.class : UserScoreboard.class;
    }

    private static DynamicScoreboard getScoreboardByState() {
        return showTopList ? new ToplistScoreboard() : new UserScoreboard();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = this.userService.getUser(player);

            if (this.scoreboards.get(player) != null) {
                DynamicScoreboard dynScoreboard = this.scoreboards.get(player);

                if (dynScoreboard.getClass() != getTypeByState()) {
                    DynamicScoreboard newSb = getScoreboardByState();
                    dynScoreboard = newSb;

                    this.setScoreboard(player, newSb);
                }

                if (dynScoreboard instanceof UserScoreboard) {
                    ((UserScoreboard) dynScoreboard).update(user, player);
                }
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

                DynamicScoreboard dynScoreboard = getScoreboardByState();

                this.setScoreboard(player, dynScoreboard);
                if (dynScoreboard instanceof UserScoreboard) {
                    ((UserScoreboard) dynScoreboard).update(user, player);
                }
            }

            Scoreboard scoreboard = player.getScoreboard();

            if (scoreboard == null) {
                scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                player.setScoreboard(scoreboard);
            }

            Objective objective = scoreboard.getObjective("userLevels");
            if (objective == null) {
                objective = scoreboard.registerNewObjective("userLevels", "dummy");
                objective.setDisplayName("§eLevel");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                player.setScoreboard(scoreboard);
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                objective.getScore(onlinePlayer).setScore(onlinePlayer.getLevel());
            }
        }
    }

    private void setScoreboard(Player player, DynamicScoreboard scoreboard) {
        this.scoreboards.put(player, scoreboard);
        scoreboard.show(player);
    }


}
