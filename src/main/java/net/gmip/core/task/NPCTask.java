package net.gmip.core.task;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.gmip.core.GMIPCore;
import net.gmip.core.utils.plugin.message.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class NPCTask extends BukkitRunnable {

    private final GMIPCore plugin;
    private boolean isNPCActive = false;

    public NPCTask(GMIPCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::updateNPCStateAsync);
    }

    private void updateNPCStateAsync() {
        NPC npc = getNPC();
        if (npc == null) return;

        String currentDay = getCurrentDay();
        List<String> activeDays = getActiveDays();
        String hologramName = plugin.npcConfigManager.get("npc", "hologram");

        if (activeDays.contains(currentDay)) {
            activateNPC(npc, hologramName);
        } else {
            deactivateNPC(npc, hologramName);
        }
    }

    private NPC getNPC() {
        int npcId = plugin.npcConfigManager.getInt("npc", "id");
        return CitizensAPI.getNPCRegistry().getById(npcId);
    }

    private void activateNPC(NPC npc, String hologramName) {
        if (isNPCActive) return;
        isNPCActive = true;
        Bukkit.getScheduler().runTask(plugin, () -> spawnNPCAndEnableHologram(npc, hologramName));
    }

    private void deactivateNPC(NPC npc, String hologramName) {
        if (!isNPCActive) return;
        isNPCActive = false;
        Bukkit.getScheduler().runTask(plugin, () -> despawnNPCAndDisableHologram(npc, hologramName));
    }

    private @NotNull String getCurrentDay() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
    }

    private @NotNull List<String> getActiveDays() {
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

    private void despawnNPCAndDisableHologram(NPC npc, String hologramName) {
        Hologram hologram = DHAPI.getHologram(hologramName);
        if (hologram == null) return;

        hologram.disable();
        npc.despawn();
        sendMessageToAllPlayers("onDeactivate");
    }

    private void spawnNPCAndEnableHologram(NPC npc, String hologramName) {
        Hologram hologram = DHAPI.getHologram(hologramName);
        if (hologram == null) return;
        hologram.enable();
        npc.spawn(npc.getStoredLocation());
        sendMessageToAllPlayers("onActivate");
    }

    private void sendMessageToAllPlayers(String key) {
        String message = plugin.npcConfigManager.get("messages", key);
        if (message != null && !message.isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.send(player, message));
        }
    }
}