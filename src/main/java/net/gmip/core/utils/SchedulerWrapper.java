package net.gmip.core.utils;

import net.gmip.core.GMIPCore;

public class SchedulerWrapper {

    private final GMIPCore plugin;

    public SchedulerWrapper(GMIPCore plugin) {
        this.plugin = plugin;
    }

    /**
     * Do a sync task.
     *
     * @param runnable the runnable.
     */
    public void doSync(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    /**
     * Do sync task which will be executed after delay.
     *
     * @param runnable The runnable.
     * @param delay    The delay in ticks.
     */

    public void doSyncLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Do sync task which will be executed after delay
     * and will be repeated every period.
     *
     * @param runnable The runnable.
     * @param delay    The delay in ticks.
     * @param period   The period in ticks.
     */

    public void doSyncRepeating(Runnable runnable, long delay, long period) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    /**
     * Do an async task.
     *
     *
     * @param runnable the runnable.
     */

    public void doAsync(Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Do async task which will be executed after delay.
     *
     * @param runnable The runnable.
     * @param delay    The delay in ticks.
     */

    public void doAsyncLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    /**
     * Do async task which will be executed after delay
     * and will be repeated every period.
     *
     * @param runnable The runnable.
     * @param delay    The delay in ticks.
     * @param period   The period in ticks.
     */

    public void doAsyncRepeating(Runnable runnable, long delay, long period) {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }
}
