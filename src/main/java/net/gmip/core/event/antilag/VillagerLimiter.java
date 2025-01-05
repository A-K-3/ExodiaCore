package net.gmip.core.event.antilag;

import net.gmip.core.event.ExodiaEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VillagerLimiter extends ExodiaEvent implements Listener {

    private static final int MAX_VILLAGERS_WITH_AI = 15;
    private static final int COOLDOWN_SECONDS = 2;
    private long lastExecutionTime = 0;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        long currentTime = System.currentTimeMillis();

        // Segundos restantes
        if (currentTime - lastExecutionTime < COOLDOWN_SECONDS * 1000) {
            return;
        }

        lastExecutionTime = currentTime;

        Chunk chunk = event.getPlayer().getLocation().getChunk();

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            int villagerWithAICount = 0;
            int villagerWithoutAICount = 0;

            for (Entity entity : chunk.getEntities()) {
                if (entity.getType() == EntityType.VILLAGER) {
                    Villager villager = (Villager) entity;
                    if (villager.hasAI()) {
                        villagerWithAICount++;
                    } else {
                        villagerWithoutAICount++;
                    }
                }
            }

            if (villagerWithAICount > MAX_VILLAGERS_WITH_AI) {
                int disableCount = villagerWithAICount - MAX_VILLAGERS_WITH_AI;
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getType() == EntityType.VILLAGER) {
                        Villager villager = (Villager) entity;
                        if (villager.hasAI() && disableCount > 0) {
                            villager.setAI(false);
                            villager.customName(Component.text("Desactivado - 15 x Chunk").color(TextColor.color(0xFF0000)));
                            villager.setCustomNameVisible(true);
                            disableCount--;
                        }
                    }
                }
            }

            if (villagerWithAICount < MAX_VILLAGERS_WITH_AI) {
                int enableCount = MAX_VILLAGERS_WITH_AI - villagerWithAICount;
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getType() == EntityType.VILLAGER) {
                        Villager villager = (Villager) entity;
                        if (!villager.hasAI() && enableCount > 0) {
                            villager.setAI(true);
                            villager.customName(null);
                            villager.setCustomNameVisible(false);
                            enableCount--;
                        }
                    }
                }
            }
        });
    }
}