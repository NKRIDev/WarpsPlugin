package fr.nkri.warps.manager;

import fr.nkri.warps.MWarp;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TimeManager implements Runnable {


    private MWarp main;
    private Player p;
    String warpName;
    private  int t;

    public TimeManager(MWarp main, Player p, String warpName){
        this.main = main;
        this.p = p;
        this.warpName = warpName;
        this.t = main.getConfig().getInt("teleport.delay");
    }

    @Override
    public void run() {

        if(t == 5){
            p.playEffect(p.getLocation(), Effect.DRAGON_BREATH, 1);
        }

        p.sendMessage(Objects.requireNonNull(main.getConfig().getString("message.teleport-in"))
                .replace("%time%", "" + t).replace("&", "§"));

        if(t == 0){
            Warp warp = main.getWarpManager().getWarp(warpName);
            p.teleport(warp.getLocation());
            p.sendMessage(Objects.requireNonNull(main.getConfig().getString("message.teleport-succes"))
                    .replace("&", "§"));

            p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
            Bukkit.getScheduler().cancelTask(t);
        }

        t--;
    }


}
