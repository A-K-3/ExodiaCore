package net.exodia.exodiaCore.manager.cooldown;

import net.exodia.exodiaCore.ExodiaCore;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {

    public static final int DEFAULT_COOLDOWN = 60;
    private final ExodiaCore plugin;

    private final Map<UUID, HashMap<CooldownType, Integer>> cooldowns;

    public CooldownManager(ExodiaCore plugin) {
        this.plugin = plugin;
        this.cooldowns = new ConcurrentHashMap<>();
    }

    public boolean hasCooldown(UUID userId, CooldownType type) {
        return cooldowns.getOrDefault(userId, new HashMap<>()).containsKey(type);
    }

    public Integer getCooldown(UUID userId, CooldownType type) {
        return cooldowns.getOrDefault(userId, new HashMap<>()).getOrDefault(type, 0);
    }

    public void setCooldown(UUID userId, CooldownType type, int seconds) {
        cooldowns.computeIfAbsent(userId, k -> new HashMap<>()).put(type, seconds);
        createScheduledCooldown(userId, type);
    }

    public void removeCooldown(UUID userId, CooldownType type) {
        if (cooldowns.containsKey(userId)) {
            cooldowns.get(userId).remove(type);
        }
    }

    public void createScheduledCooldown(UUID userId, CooldownType type) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int timeLeft = getCooldown(userId, type);
                setCooldown(userId, type, timeLeft - 1);
                if (timeLeft == 0) {
                    cooldowns.get(userId).remove(type);
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }
}