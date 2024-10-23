package net.gmip.core.utils;

import net.gmip.core.utils.plugin.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {


    /**
     * Play a minecraft sound to the player.
     *
     * @param player The player related.
     * @param sound  The sound to play.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void play(Player player, String sound, float volume, float pitch) {

        if (sound == null || player == null) return;

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Play a minecraft sound to the player list.
     *
     * @param players The collection of player's related.
     * @param sound   The sound to play.
     * @param volume  The volume of the sound.
     * @param pitch   The pitch of the sound.
     */
    public static void play(Collection<Player> players, String sound, float volume, float pitch) {

        if (sound == null) return;

        players.forEach(player -> play(player, sound, volume, pitch));
    }

    /**
     * Play a XSeries Sound parsed to a Minecraft Sound
     *
     * @param player The player related.
     * @param sound  The XSeries Sound to play.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void play(Player player, Sound sound, float volume, float pitch) {

        if (sound == null || player == null) return;

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static Collection<Player> getFiltered(String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).collect(Collectors.toList());
    }

    public static boolean runCommand(CommandSender sender, String command) {

        if (sender == null || command == null || command.isEmpty()) return false;

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginUtils.PLUGIN, () -> Bukkit.dispatchCommand(sender, command));

        return true;
    }

    public static String cleanStackTree(StackTraceElement[] e) {

        return Arrays.toString(e)
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .replace(",", "\n")
                .lines()
                .filter(l -> l.contains("net.exodia"))
                .map(a -> a.substring(a.lastIndexOf("//") + 2))
                .collect(Collectors.joining("\n"));

    }

    /* Parámetros para pasar: Numeros + Letra

        y/Y = Año
        m/M = Mes
        w/W = Semana
        d/D = Día
        h/H = Hora
        m = Minuto
        s/S = Segundo

        Ejemplo válido: 3d5h30m50s
        Ejemplo válido: 3d5h30m50s3d5h30m50s
        Ejemplo inválido: d6h
        Ejemplo inválido: 4y4
     */
    public static Date StringToDate(String dateString) {

        List<String> validDateFormats = List.of("y", "m", "w", "d", "h", "s");

        if (dateString == null || dateString.isEmpty()) return null;

        dateString = dateString.replaceAll("\\s", "");
        if (validDateFormats.stream().noneMatch(dateString.toLowerCase()::contains)) return null;

        List<String> dateFormats = new ArrayList<>();

        if (!Character.isDigit(dateString.charAt(0)) || Character.isDigit(dateString.length())) return null;

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < dateString.length(); i++) {
            char c = dateString.charAt(i);
            if (Character.isDigit(c)) {
                s.append(c);
            } else {
                if (i + 1 < dateString.length() && !Character.isDigit(dateString.charAt(i + 1))) return null;
                dateFormats.add(s.toString() + c);
                s = new StringBuilder();
            }
        }

        Calendar now = Calendar.getInstance();

        for (String format : dateFormats) {

            String number = format.replaceAll("[^0-9]", "");
            String type = format.replaceAll("[0-9]", "");

            if (!type.equals("m")) type = type.toUpperCase();

            switch (type) {
                case "Y" -> now.add(Calendar.YEAR, Integer.parseInt(number));
                case "M" -> now.add(Calendar.MONTH, Integer.parseInt(number));
                case "W" -> now.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(number));
                case "D" -> now.add(Calendar.DAY_OF_YEAR, Integer.parseInt(number));
                case "H" -> now.add(Calendar.HOUR_OF_DAY, Integer.parseInt(number));
                case "m" -> now.add(Calendar.MINUTE, Integer.parseInt(number));
                case "S" -> now.add(Calendar.SECOND, Integer.parseInt(number));
            }
        }

        return now.getTime();
    }

    public static @NotNull String calculateTime(long time) {
        long years = TimeUnit.MILLISECONDS.toDays(time) / 365;
        long months = TimeUnit.MILLISECONDS.toDays(time) / 30;
        long weeks = TimeUnit.MILLISECONDS.toDays(time) / 7;
        long days = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

        return (years > 0 ? years + "y " : "") + (months > 0 ? months + "m " : "") + (weeks > 0 ? weeks + "w " : "") + (days > 0 ? days + "d " : "") + (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + (seconds > 0 ? seconds + "s " : "");
    }

    public static @NotNull String calculateTime(int seconds) {
        int days = seconds / 86400;
        int hours = (seconds % 86400) / 3600;
        int minutes = ((seconds % 86400) % 3600) / 60;
        int secs = ((seconds % 86400) % 3600) % 60;

        return (days > 0 ? days + "d " : "") + (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + (secs > 0 ? secs + "s " : "");
    }


    public static @Nullable String getTextAsParameter(String @NotNull [] args, int index) {
        StringBuilder text = new StringBuilder();
        for (int i = index; i < args.length; i++) {
            if (args[i] != null) text.append(args[i]).append(" ");
        }

        if (text.toString().trim().isEmpty()) return null;
        return text.toString();

    }

    static public String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    static public Location getLocationString(final String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }
}
