package net.exodia.exodiaCore.event.kothbossbar;

import me.ulrich.koth.Koth;
import me.ulrich.koth.api.KothAPIManager;
import me.ulrich.koth.data.KothData;
import me.ulrich.koth.events.KothCaptureEvent;
import net.exodia.exodiaCore.event.ExodiaEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.*;

import java.util.UUID;


public class onKothStartEvent extends ExodiaEvent {

    private KothAPIManager kothAPIManager;
    private BossBar bossbar;

    @EventHandler
    public void kothBossBar(KothCaptureEvent e) {
        UUID kothUUID = e.getKothUUID();
        String player = e.getPlayer().getName();
        String kothname = plugin.kothAPIManager.getKothName(kothUUID).get();


        System.out.println("start bossbar");
        System.out.println(player + " está capturando el KOTH " + kothname);

// TODO CONFIG
    }
}
