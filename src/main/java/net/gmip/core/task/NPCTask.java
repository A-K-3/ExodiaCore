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
import java.util.*;
import java.util.stream.Collectors;

public class NPCTask extends BukkitRunnable {

    private final GMIPCore plugin;
    private boolean isNPCActive = false;

    public NPCTask(GMIPCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::checkAndUpdateNPCState);
    }

    private void checkAndUpdateNPCState() {
        getNPC().ifPresent(npc -> {
            String currentDay = getCurrentDay();
            String hologramName = plugin.npcConfigManager.get("npc", "hologram");

            if (getActiveDays().contains(currentDay)) {
                activateNPCIfNeeded(npc, hologramName);
            } else {
                deactivateNPCIfNeeded(npc, hologramName);
            }
        });
    }

    private Optional<NPC> getNPC() {
        int npcId = plugin.npcConfigManager.getInt("npc", "id");
        return Optional.ofNullable(CitizensAPI.getNPCRegistry().getById(npcId));
    }

    private void activateNPCIfNeeded(NPC npc, String hologramName) {
        if (!isNPCActive) {
            isNPCActive = true;
            Bukkit.getScheduler().runTask(plugin, () -> toggleNPCAndHologram(npc, hologramName, true, "onActivate"));
        }
    }

    private void deactivateNPCIfNeeded(NPC npc, String hologramName) {
        if (isNPCActive) {
            isNPCActive = false;
            Bukkit.getScheduler().runTask(plugin, () -> toggleNPCAndHologram(npc, hologramName, false, "onDeactivate"));
        }
    }

    private void toggleNPCAndHologram(NPC npc, String hologramName, boolean activate, String messageKey) {
        getHologram(hologramName).ifPresent(hologram -> {
            if (activate) {
                hologram.enable();
                npc.spawn(npc.getStoredLocation());
            } else {
                hologram.disable();
                npc.despawn();
            }
            sendMessageToAllPlayers(messageKey);
        });
    }

    private @NotNull String getCurrentDay() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
    }

    private @NotNull List<String> getActiveDays() {
        ConfigurationSection daysSection = plugin.npcConfigManager.getSection("schedule.days");
        return plugin.npcConfigManager.getKeys(daysSection).stream()
                .filter(day -> plugin.npcConfigManager.getBoolean("schedule.days", day))
                .collect(Collectors.toList());
    }

    private Optional<Hologram> getHologram(String hologramName) {
        return Optional.ofNullable(DHAPI.getHologram(hologramName));
    }

    private void sendMessageToAllPlayers(String key) {
        String message = plugin.npcConfigManager.get("messages", key);
        if (message != null && !message.isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.send(player, message));
        }
    }
}
