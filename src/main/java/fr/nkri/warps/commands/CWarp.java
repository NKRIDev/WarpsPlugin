package fr.nkri.warps.commands;

import fr.nkri.warps.MWarp;
import fr.nkri.warps.manager.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CWarp implements CommandExecutor {

    public MWarp main;
    public CWarp(MWarp main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage("NO PLAYER");
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 0){
            sendHelp(p);
            return true;
        }

        if(args[0].equalsIgnoreCase("add")){

            if(!p.hasPermission("warps.add")){
                p.sendMessage("§cVous n'avez pas la permission !");
                return true;
            }

            String warpName = args[1];

            main.getWarpManager().setWarp(warpName, p.getLocation());
            main.getDataManager().saveWarps();
            p.sendMessage("§aVous venez de crée le warp §2" + warpName + " §aavec succès.");

        }
        else if(args[0].equalsIgnoreCase("remove")){

            if(!p.hasPermission("warps.remove")){
                p.sendMessage("§cVous n'avez pas la permission !");
                return true;
            }

            String warpName = args[1];

            if(!main.getWarpManager().getWarpsName().contains(warpName)){
                p.sendMessage(main.getConfig().getString("message.none").replace("&", "§"));
                return true;
            }
            Warp warp = main.getWarpManager().getWarp(warpName);

            main.getWarpManager().removeWarp(warp);
            main.getDataManager().saveWarps();

            p.sendMessage("§cVous venez de supprimer le warp §4" + warpName + " §cavec succès.");

        }


        return true;
    }

    private void sendHelp(Player p){
        p.sendMessage("§8/warp <remove|add> : Gérer les warps");
        p.sendMessage("§8/warps <list> : Voir les warps");
        p.sendMessage("§8/warps <nom d’un warps> : Se téléporter à un warp");
    }


}
