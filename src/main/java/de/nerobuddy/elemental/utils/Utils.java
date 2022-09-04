package de.nerobuddy.elemental.utils;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 22:55
 */

public final class Utils {

    private Utils() {
    }

    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void msgPlayer(final Player player, final String... strings) {
        for (String s : strings) {
            player.sendMessage(color(s));
        }
    }

    public static void broadcastMessage(final String... strings) {
        for (String s : strings) {
            Bukkit.broadcastMessage(color(s));
        }
    }

    public static void broadcastMessageWithoutOwner(final Player player, final String... strings) {
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != player) {
                players.add(p);
            }
        }
        for (String s : strings) {
            for (Player p : players) {
                msgPlayer(p, s);
            }
        }
    }

    public static void setConfigDefaults() {
        Elemental plugin = Elemental.getPlugin();
        FileConfiguration config = plugin.getConfig();
        config.addDefault("prefix", "&8[&eElemental&8] ");
        config.addDefault("joinMessage", "&c%player% &ehas joined the server");
        config.addDefault("quitMessage", "&c%player% &ehas left the server");
        config.addDefault("afk", "");
        config.addDefault("afk.permission.self", "elemental.afk");
        config.addDefault("afk.permission.others", "elemental.afk.others");
        config.addDefault("afk.message.self.afk", "&eYou are now AFK!");
        config.addDefault("afk.message.self.noafk", "&eYou are no longer AFK!");
        config.addDefault("afk.message.others.afk", "&c%player% &eis now AFK!");
        config.addDefault("afk.message.others.noafk", "&c%player% &eis no longer AFK!");
        config.addDefault("feed", "");
        config.addDefault("feed.permission.self", "elemental.feed");
        config.addDefault("feed.permission.others", "elemental.feed.others");
        config.addDefault("feed.message.self", "&eYou have been feed!");
        config.addDefault("feed.message.others", "&c%player% &has been feed!");
        config.addDefault("fly", "");
        config.addDefault("fly.permission.self", "elemental.fly");
        config.addDefault("fly.permission.others", "elemental.fly.others");
        config.addDefault("fly.message.self.fly", "&eYou can fly now!");
        config.addDefault("fly.message.self.nofly", "&eYou can't fly anymore!");
        config.addDefault("fly.message.others.fly", "&c%player% &can fly now!");
        config.addDefault("fly.message.others.nofly", "&c%player% &can't fly anymore!");

    }

}
