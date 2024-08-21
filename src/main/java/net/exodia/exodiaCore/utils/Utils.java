package net.exodia.exodiaCore.utils;

import com.cryptomorin.xseries.XSound;
import com.reussy.development.staffutilities.plugin.StaffUtilitiesPlugin;
import com.reussy.development.staffutilities.plugin.exceptions.PluginErrorException;
import com.reussy.development.staffutilities.plugin.module.staff.CustomItemStack;
import com.reussy.development.staffutilities.plugin.sql.entity.types.SanctionType;
import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.utils.plugin.message.ExodusMessageType;
import net.exodia.exodiaCore.utils.plugin.message.TranslateHexColorCodes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Utils {

    /* Clase con utilidades para el plugin */



    public static String colorize(String message) {
        message = TranslateHexColorCodes.translateHexColorCodes("&#", "", message);
        message = translateAlternateColorCodes('&', message);

        return message;
    }


    /**
     * Send a colorized message to the player.
     *
     * @param player  The player related.
     * @param message The message to send.
     */
    public static void send(Player player, String message) {
        if (message == null || message.isEmpty() || player == null) return;

        player.sendMessage(colorize(message));
    }

    /**
     * Send a colorized message to a command sender.
     *
     * @param sender  The command sender related.
     * @param message The message to send.
     */
    public static void send(CommandSender sender, String message) {
        if (message == null || message.isEmpty() || sender == null) return;

        sender.sendMessage(colorize(message));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player  The player related.
     * @param message The message to send.
     */
    public static void send(Player player, List<String> message) {
        if (message == null || message.isEmpty() || player == null) return;
        message.forEach(line -> send(player, line));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players The list of player's related.
     * @param message The message to send.
     */
    public static void send(Collection<Player> players, String message) {
        if (message == null || message.isEmpty() || players.isEmpty()) return;

        players.forEach(player -> send(player, message));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players The list of player's related.
     * @param message The message to send.
     */
    public static void send(Collection<Player> players, List<String> message) {
        if (message == null || message.isEmpty() || players.isEmpty()) return;

        players.forEach(player -> message.forEach(line -> send(player, line)));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player       The player related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Player player, String message, String[][] placeholders) {
        if (message == null || message.isEmpty() || player == null) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        send(player, colorize(message));
    }

    /**
     * Send a colorized message to the sender with placeholders.
     *
     * @param sender       The sender related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(CommandSender sender, String message, String[][] placeholders) {
        if (message == null || message.isEmpty() || sender == null) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        send(sender, colorize(message));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player       The player related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Player player, List<String> message, String[][] placeholders) {
        if (message == null || message.isEmpty() || player == null) return;

        for (String[] placeholder : placeholders) {
            message = message.stream().map(line -> line.replace(placeholder[0], placeholder[1])).collect(Collectors.toList());
        }

        message.forEach(line -> send(player, line));
    }

    /**
     * Send a colorized message to the command sender.
     *
     * @param sender       The sender of the command.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(CommandSender sender, List<String> message, String[][] placeholders) {
        if (message == null || message.isEmpty() || sender == null) return;

        if (placeholders != null) {
            for (String[] placeholder : placeholders) {
                message = message.stream().map(line -> line.replace(placeholder[0], placeholder[1])).collect(Collectors.toList());
            }
        }

        message.forEach(line -> send(sender, line));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players      The players related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Collection<Player> players, String message, String[][] placeholders) {
        if (message == null || message.isEmpty()) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        for (Player player : players) {
            send(player, message, placeholders);
        }
    }

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
    public static void play(Player player, XSound sound, float volume, float pitch) {

        if (sound == null || player == null) return;

        sound.play(player, volume, pitch);
    }

    /**
     * Play a XSeries Sound parsed to a Minecraft Sound
     * This method follow the format SOUND:VOLUME:PITCH
     *
     * @param player The player related.
     * @param format The format of the sound.
     */
    public static void play(Player player, String format) {

        if (format == null || format.isEmpty() || format.equalsIgnoreCase("none")) return;

        String[] split = format.split(":");

        if (split.length != 3) {
            throw new PluginErrorException("The format for the sound " + format + " is invalid. The format must be SOUND:VOLUME:PITCH", new IndexOutOfBoundsException());
        }

        Optional<XSound> xSound = XSound.matchXSound(split[0]);
        float volume = split[1].length() == 0 ? 1 : Float.parseFloat(split[1]);
        float pitch = split[2].length() == 0 ? 1 : Float.parseFloat(split[2]);

        xSound.ifPresent(value -> play(player, value, volume, pitch));
    }

    public static Collection<Player> getFiltered(String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).collect(Collectors.toList());
    }

    public static boolean runCommand(CommandSender sender, String command) {

        if (sender == null || command == null || command.isEmpty()) return false;

        Bukkit.getScheduler().scheduleSyncDelayedTask(StaffUtilitiesPlugin.getPlugin(StaffUtilitiesPlugin.class), () -> Bukkit.dispatchCommand(sender, command));

        return true;
    }

    public static String cleanStackTree(StackTraceElement[] e) {

        return Arrays.toString(e)
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .replace(",", "\n")
                .lines()
                .filter(l -> l.contains("com.reussy"))
                .map(a -> a.substring(a.lastIndexOf("//") + 2))
                .collect(Collectors.joining("\n"));

    }

    @Contract(pure = true)
    public static String getPrettyName(@NotNull SanctionType sanctionType) {
        return switch (sanctionType) {
            case BAN -> "Ban";
            case MUTE -> "Mute";
            case KICK -> "Kick";
            case WARN -> "Warn";
        };
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

    public static @NotNull List<CustomItemStack> saveInventory(@NotNull Inventory inventory) {
        final List<CustomItemStack> customItemStacks = new ArrayList<>();

        for (int i = 0; i <= inventory.getContents().length; i++) {

            if (inventory.getItem(i) == null) continue;

            ItemStack item = inventory.getItem(i);
            customItemStacks.add(new CustomItemStack(i, item));
        }

        return customItemStacks;
    }

    public static @Nullable String getTextAsParameter(String @NotNull [] args, int index) {
        StringBuilder text = new StringBuilder();
        for (int i = index; i < args.length; i++) {
            if (args[i] != null) text.append(args[i]).append(" ");
        }

        if (text.toString().trim().length() == 0) return null;
        return text.toString();

    }

    static public String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    static public Location getLocationString(final String s) {
        if (s == null || s.trim().equals("")) {
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
