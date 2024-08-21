package net.exodia.exodiaCore.manager.cooldown;

import com.reussy.development.staffutilities.plugin.StaffUtilitiesPlugin;
import com.reussy.development.staffutilities.plugin.sql.entity.UserEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {

    public static final int DEFAULT_COOLDOWN = 60;
    private final StaffUtilitiesPlugin plugin;

    private final Map<UserEntity, HashMap<CooldownType, Integer>> cooldowns;

    public CooldownManager(StaffUtilitiesPlugin plugin) {
        this.plugin = plugin;
        this.cooldowns = new ConcurrentHashMap<>();
    }

    public boolean hasCooldown(UserEntity user, CooldownType type) {
        return cooldowns.getOrDefault(user, new HashMap<>()).containsKey(type);
    }

    public Integer getCooldown(UserEntity user, CooldownType type) {
        return cooldowns.getOrDefault(user, new HashMap<>()).getOrDefault(type, 0);
    }

    public void setCooldown(UserEntity user, CooldownType type, int seconds) {

        if (cooldowns.containsKey(user)) {

            if (cooldowns.get(user).containsKey(type) && cooldowns.get(user).containsKey(type)) {
                cooldowns.get(user).put(type, seconds);
            } else {
                cooldowns.get(user).put(type, seconds);
                createScheduledCooldown(user, type);
            }
        } else {
            cooldowns.put(user, new HashMap<>());
            cooldowns.get(user).put(type, seconds);
            setCooldown(user, type, seconds);
            createScheduledCooldown(user, type);
        }
    }

    public void removeCooldown(UserEntity user, CooldownType type) {
        if (cooldowns.containsKey(user) && cooldowns.get(user).containsKey(type)) setCooldown(user, type, 0);
    }

    public void createScheduledCooldown(UserEntity user, CooldownType type) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int timeLeft = getCooldown(user, type);
                setCooldown(user, type, timeLeft - 1);
                if (timeLeft == 0) {
                    cooldowns.get(user).remove(type);
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }
}
