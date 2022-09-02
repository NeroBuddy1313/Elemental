package de.nerobuddy.elemental.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

}
