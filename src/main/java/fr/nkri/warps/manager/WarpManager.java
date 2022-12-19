package fr.nkri.warps.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

public class WarpManager {

    private HashMap<String, Warp> warps = new HashMap<>();

    public void setWarp(String name, Location location){
        setWarp(new Warp(name, location));
    }

    public Warp setWarp(Warp warp){
        return warps.put(warp.getName().toLowerCase(), warp);
    }

    public Warp getWarp(String name){
        return warps.get(name.toLowerCase());
    }

    public void removeWarp(Warp warp){
        warps.remove(warp.getName().toLowerCase());
    }

    public ArrayList<String> getWarpsName(){
        return new ArrayList<>(warps.keySet());
    }

    public ArrayList<Warp> getWarps(){

        ArrayList<Warp> warpsList = new ArrayList<>();

        warps.forEach((key, value) -> warpsList.add(value));

        return warpsList;
    }

    public Warp deserialize(String warp){

        String[] args = warp.split(",");
        String name = args[0];
        String worldS = args[1];
        String xS = args[2];
        String yS = args[3];
        String zS = args[4];
        String pitchS = args[5];
        String yawS = args[6];
        try {
            World world = Bukkit.getWorld(worldS);
            double x = Double.parseDouble(xS);
            double y = Double.parseDouble(yS);
            double z = Double.parseDouble(zS);
            float pitch = Float.parseFloat(pitchS);
            float yaw = Float.parseFloat(yawS);
            Location loc = new Location(world, x, y, z, yaw, pitch);
            return new Warp(name, loc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
