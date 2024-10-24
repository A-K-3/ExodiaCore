package net.gmip.core.task;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.gmip.core.GMIPCore;
import net.gmip.core.utils.plugin.message.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class NPCTask extends BukkitRunnable {

    private final GMIPCore plugin;

    public NPCTask(GMIPCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        checkAndUpdateNPCState();
    }

    private void checkAndUpdateNPCState() {

        // Los NPC's desaparecen si no hay jugadores conectados
        if (Bukkit.getOnlinePlayers().isEmpty()) return;

        int npcId = plugin.npcConfigManager.getInt("npc", "id");
        NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
        if (npc == null) return;

        String weekDay = getCurrentWeekDay();
        List<String> activeDays = getActiveDays();

        String hologram = plugin.npcConfigManager.get("npc", "hologram");

        if (!activeDays.contains(weekDay)) {
            despawnNPCAndDisableHologram(npc, hologram);
        } else {
            spawnNPCIfNotSpawned(npc, hologram);
        }
    }

    private String getCurrentWeekDay() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
    }

    private List<String> getActiveDays() {
        List<String> activeDays = new ArrayList<>();
        ConfigurationSection daysSection = plugin.npcConfigManager.getSection(plugin.npcConfigManager.getSection("schedule"), "days");
        Set<String> days = plugin.npcConfigManager.getKeys(daysSection);

        for (String day : days) {
            if (plugin.npcConfigManager.getBoolean("schedule.days", day)) {
                activeDays.add(day);
            }
        }
        return activeDays;
    }

    private void despawnNPCAndDisableHologram(NPC npc, String hologram) {
        if (npc.isSpawned()) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                npc.despawn();
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "hologram disable " + hologram);
                sendMessageToAllPlayers("onDeactivate");
            });
        }
    }

    private void spawnNPCIfNotSpawned(NPC npc, String hologram) {
        if (!npc.isSpawned()) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                npc.spawn(npc.getStoredLocation());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "hologram enable " + hologram);
                sendMessageToAllPlayers("onActivate");
            });
        }
    }

    private void sendMessageToAllPlayers(String key) {
        String message = plugin.npcConfigManager.get("messages", key);
        if (message != null && !message.isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.send(player, message));
        }
    }
}