package fr.nkri.warps;

import fr.nkri.warps.commands.CWarp;
import fr.nkri.warps.commands.CWarps;
import fr.nkri.warps.manager.DataManager;
import fr.nkri.warps.manager.WarpManager;
import org.bukkit.plugin.java.JavaPlugin;


public class MWarp extends JavaPlugin {

    private WarpManager warpManager = new WarpManager();
    private DataManager dataManager;

    @Override
    public void onEnable(){

        saveDefaultConfig();

        dataManager = new DataManager(this);
        dataManager.loadWarps();

        getCommand("warp").setExecutor(new CWarp(this));
        getCommand("warps").setExecutor(new CWarps(this));
    }

    @Override
    public void onDisable() {
        dataManager.saveWarps();
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
