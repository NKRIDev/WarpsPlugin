package fr.nkri.warps.manager;

import fr.nkri.warps.MWarp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Objects;

public class DataManager {

    private MWarp main;
    private File file;
    private FileConfiguration config;

    public DataManager(MWarp main){
        this.main = main;
        file = new File(main.getDataFolder(), "warps.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void loadWarps(){

        if(getFile().exists()
                && !file.exists()){

            loadWarpsWithObject();
            getFile().delete();
            saveWarps();

        }

        ArrayList<String> strings = getChildrenConfig(config, "warps");

        for(String name : strings){

            String world = config.getString("warps." + name + ".world");
            double x = config.getDouble("warps." + name + ".x");
            double y = config.getDouble("warps." + name + ".y");
            double z = config.getDouble("warps." + name + ".z");
            float yaw = (float) config.getDouble("warps." + name + ".yaw");
            float pitch = (float) config.getDouble("warps." + name + ".pitch");

            Warp warp = new Warp(name, world, x, y, z, yaw, pitch);
            main.getWarpManager().setWarp(warp);
        }

    }

    public void loadWarpsWithObject(){

        File file = getFile();

        if(file.exists()){

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
                Object result = objectInputStream.readObject();

                objectInputStream.close();

                if(result != null){

                    ArrayList<String> strings = (ArrayList<String>) result;
                    for(String s : strings){

                        try{
                            main.getWarpManager().setWarp(main.getWarpManager().deserialize(s));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void saveWarps(){

        for(String string : config.getKeys(true)){
            config.set(string, null);
        }

        for(Warp warp : main.getWarpManager().getWarps()){

            config.set("warps." + warp.getName() + ".world", warp.getLocation().getWorld().getName());
            config.set("warps." + warp.getName() + ".x", warp.getLocation().getX());
            config.set("warps." + warp.getName() + ".y", warp.getLocation().getY());
            config.set("warps." + warp.getName() + ".z", warp.getLocation().getZ());
            config.set("warps." + warp.getName() + ".yaw", warp.getLocation().getYaw());
            config.set("warps." + warp.getName() + ".pitch", warp.getLocation().getPitch());

        }

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getChildrenConfig(FileConfiguration config, String path) {
        ArrayList<String> toRet = new ArrayList<String>();
        try {
            toRet.addAll(Objects.requireNonNull(config.getConfigurationSection(path)).getKeys(false));
        } catch (Exception e) {
            toRet.clear();
        }
        return toRet;
    }

    public File getFile() {
        String warpData = "warps.data";
        return new File("plugins/" + main.getName() + "/" + warpData);
    }
}
