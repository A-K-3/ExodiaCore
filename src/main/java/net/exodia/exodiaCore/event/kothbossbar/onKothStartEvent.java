package net.exodia.exodiaCore.event.kothbossbar;

import me.clip.placeholderapi.PlaceholderAPI;
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

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class onKothStartEvent extends ExodiaEvent implements Listener {

    private BossBar bossbar;
    UUID kothUUID = null;
    String kothname = null;

    @EventHandler
    public void kothStartEnd(KothStartEvent e) {
        kothUUID = e.getKothUUID();
        kothname = plugin.kothAPIManager.getKothName(kothUUID).get();
        if (bossbar != null) {
            bossbar.removeAll();
        }
        bossbar = Bukkit.createBossBar("Capturando KOTH: " + kothname, BarColor.RED, BarStyle.SOLID);
        bossbar.setVisible(true);

        plugin.pluginScheduler.doAsyncRepeating(() -> {
            updateBossBar();
        },0L, 20L);
    }

    @EventHandler
    public void onKothEnd(KothEndEvent e) {
        if (bossbar != null) {
            bossbar.removeAll();
            bossbar = null;
        }
    }

    private void updateBossBar() {
        if (bossbar != null) {
            bossbar.removeAll();
            Player firstPlayer = Bukkit.getOnlinePlayers().iterator().next();
            String kothPlayer = PlaceholderAPI.setPlaceholders(firstPlayer, "%ukoth_{" + kothname + "}_control_player%");
            String kothTimeLeft = PlaceholderAPI.setPlaceholders(firstPlayer, "%ukoth_{" + kothname + "}_timeleft%");
            String kothPlayerClan = PlaceholderAPI.setPlaceholders(firstPlayer, "%ukoth_{" + kothname + "}_control_group%");
            Integer kothDefaultTimeSeconds = plugin.kothAPIManager.getKoth(kothUUID).get().getCaptureTime();
            int minutes = kothDefaultTimeSeconds / 60;
            int seconds = kothDefaultTimeSeconds % 60;
            String kothDefaultTime = minutes + "m " + seconds + "s";
            if (kothPlayer != null && !kothPlayer.equalsIgnoreCase("§cNinguno")) {
                String title = "§cTiempo restante: §b" + kothTimeLeft + " §cCapturando: §b" + kothPlayer + " §c(" + kothPlayerClan + ")";
                bossbar.setTitle(title);
                // TODO: Hacer que el progress vaya en función del tiempo restante %ukoth_{end}_timeleft%
                bossbar.setProgress(1);
            } else {
                String title = "§4Tiempo restante: §b " + kothDefaultTime + " §4Capturando: §b§l--- §7§l---";
                bossbar.setTitle(title);
                bossbar.setProgress(0);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                bossbar.addPlayer(player);
            }
        }
    }
}
