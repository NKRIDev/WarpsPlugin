package fr.nkri.warps.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Warp {

    private String name;
    private String world;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public Warp(String name, Location location){
        setName(name);
        setLocation(location);
    }

    public Warp(String name, String world, double x, double y, double z, float yaw, float pitch){

        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(Location location){
        world = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
    }

    public Location getLocation(){
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

}
