/*package me.skylands.skypvp.pve.bosses.pathfinders;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PathfinderGoalReturnToTotem extends PathfinderGoal {

    private double speed;

    private EntityInsentient entity;

    private Location location;

    private Navigation navigation;

    private int currentLoc;

    public PathfinderGoalReturnToTotem(EntityInsentient entity, Location location, double speed) {
        this.entity = entity;
        this.location = location;
        this.navigation = (Navigation) this.entity.getNavigation();
        this.speed = speed;
    }

    public boolean a() {
        return true;
    }

    public void c() {
        if(this.location.distance(this.entity.getBukkitEntity().getLocation()) > 5) {
            this.navigation.a(this.location.getX(), this.location.getY(), this.location.getZ());
        }
    }
    public void e() {

    }
}*/