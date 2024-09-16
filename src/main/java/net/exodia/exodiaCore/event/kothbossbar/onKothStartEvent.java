package net.exodia.exodiaCore.event.kothbossbar;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ulrich.koth.Koth;
import me.ulrich.koth.api.KothAPIManager;
import me.ulrich.koth.data.KothData;
import me.ulrich.koth.events.ExtEvent;
import me.ulrich.koth.events.KothCaptureEvent;
import me.ulrich.koth.events.KothEndEvent;
import me.ulrich.koth.events.KothStartEvent;
import net.exodia.exodiaCore.event.ExodiaEvent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class onKothStartEvent extends ExodiaEvent implements Listener {

    private BossBar bossbar;

    @EventHandler
    public void kothStartEnd(KothStartEvent e) {
        UUID kothUUID = e.getKothUUID();
        String kothname = plugin.kothAPIManager.getKothName(kothUUID).get();

        // Crear y mostrar la BossBar cuando comience la captura del KOTH
        if (bossbar != null) {
            // Eliminar la BossBar anterior si existe
            bossbar.removeAll();
        }
        bossbar = Bukkit.createBossBar("Capturando KOTH: " + kothname, BarColor.RED, BarStyle.SOLID);
        bossbar.setVisible(true);

        // Mostrar la nueva BossBar a todos los jugadores
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            bossbar.addPlayer(onlinePlayer);
        }

        // Iniciar la verificación periódica del estado del KOTH
        startMonitoring(kothname);
    }

    @EventHandler
    public void kothBossBar(KothCaptureEvent e) {
        UUID kothUUID = e.getKothUUID();
        String kothname = plugin.kothAPIManager.getKothName(kothUUID).get();
        startMonitoring(kothname);
    }

    @EventHandler
    public void onKothEnd(KothEndEvent e) {
        if (bossbar != null) {
            bossbar.removeAll();
            bossbar = null;
        }
    }

    private void startMonitoring(String kothname) {
        new BukkitRunnable() {
            @Override
            public void run() {
                updateBossBar(kothname);
            }
        }.runTaskTimer(plugin, 0L, 20L); // Revisa cada segundo (20 ticks)
    }

    private void updateBossBar(String kothname) {
        if (bossbar != null) {
            bossbar.removeAll();

            for (Player player : Bukkit.getOnlinePlayers()) {
                String kothController = PlaceholderAPI.setPlaceholders(player, "%ukoth_{" + kothname + "}_control_player%");
                String kothTimeLeft = PlaceholderAPI.setPlaceholders(player, "%ukoth_{" + kothname + "}_timeleft%");
                String kothDefaultTime = PlaceholderAPI.setPlaceholders(player, "%ukoth_{" + kothname + "}_capture_time_formated%");

                if (kothController != null && !kothController.isEmpty() && !kothController.equalsIgnoreCase("§cNinguno")) {
                    String title = "Capturando KOTH: " + kothController + " - Tiempo restante: " + kothTimeLeft;
                    bossbar.setTitle(title);
                    // TODO: Hacer que el progress vaya en función del tiempo restante %ukoth_{end}_timeleft%
                    bossbar.setProgress(1);
                    break;
                } else {
                    String title = "Capturando KOTH: Nadie - Tiempo restante: " + kothDefaultTime;
                    bossbar.setTitle(title);
                    bossbar.setProgress(0.02);
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                bossbar.addPlayer(onlinePlayer);
            }
        }
    }
}
