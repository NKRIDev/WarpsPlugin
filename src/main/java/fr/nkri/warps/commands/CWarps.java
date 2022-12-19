package fr.nkri.warps.commands;

import fr.nkri.warps.MWarp;
import fr.nkri.warps.manager.TimeManager;
import fr.nkri.warps.manager.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CWarps implements CommandExecutor {

    public MWarp main;
    public CWarps(MWarp main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage("NO PLAYER");
            return true;
        }

        Player p = (Player) sender;


        if(args.length > 1){
            sendHelp(p);
            return false;
        }

        if(args[0].equalsIgnoreCase("list")){
            p.sendMessage(main.getConfig().getString("message.list")
                    .replace("%list%", main.getWarpManager().getWarpsName().toString())
                    .replace("&", "§"));
            return true;
        }

        String warpName = args[0];

        if(!main.getWarpManager().getWarpsName().contains(warpName)){
            p.sendMessage(main.getConfig().getString("message.none").replace("&", "§"));
            return true;
        }

        Bukkit.getScheduler().runTaskTimer(main, new TimeManager(main, p, warpName), 0L, 20l);

        return true;
    }

    private void sendHelp(Player p){
        p.sendMessage("§8/warp <remove|add> : Gérer les warps");
        p.sendMessage("§8/warps <list> : Voir les warps");
        p.sendMessage("§8/warps <nom d’un warps> : Se téléporter à un warp");
    }



}
