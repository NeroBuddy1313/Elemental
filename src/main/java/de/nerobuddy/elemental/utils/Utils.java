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

        // prefix
        config.addDefault("prefix", "&8[&eElemental&8] ");

        // joinMessage & quitMessage
        config.addDefault("joinMessage", "&c%player% &ehas joined the server");
        config.addDefault("quitMessage", "&c%player% &ehas left the server");

        // afk command messages
        config.addDefault("afk", "");
        config.addDefault("afk.permission.self", "elemental.afk");
        config.addDefault("afk.permission.others", "elemental.afk.others");
        config.addDefault("afk.message.self.afk", "&eYou are now AFK!");
        config.addDefault("afk.message.self.noafk", "&eYou are no longer AFK!");
        config.addDefault("afk.message.others.afk", "&c%player% &eis now AFK!");
        config.addDefault("afk.message.others.noafk", "&c%player% &eis no longer AFK!");

        // isafk command messages
        config.addDefault("isafk", "");
        config.addDefault("isafk.permission", "elemental.isafk");
        config.addDefault("isafk.message.self.afk", "&eYou are currently AFK!");
        config.addDefault("isafk.message.self.noafk", "&eYou are currently not AFK!");
        config.addDefault("isafk.message.others.afk", "&c%player% &eis currently AFK!");
        config.addDefault("isafk.message.others.noafk", "&c%player% &eis currently not AFK!");

        // feed command messages
        config.addDefault("feed", "");
        config.addDefault("feed.permission.self", "elemental.feed");
        config.addDefault("feed.permission.others", "elemental.feed.others");
        config.addDefault("feed.message.self", "&eYou have been feed!");
        config.addDefault("feed.message.others", "&c%player% &has been feed!");

        // fly command messages
        config.addDefault("fly", "");
        config.addDefault("fly.permission.self", "elemental.fly");
        config.addDefault("fly.permission.others", "elemental.fly.others");
        config.addDefault("fly.message.self.fly", "&eYou can fly now!");
        config.addDefault("fly.message.self.nofly", "&eYou can't fly anymore!");
        config.addDefault("fly.message.others.fly", "&c%player% &can fly now!");
        config.addDefault("fly.message.others.nofly", "&c%player% &can't fly anymore!");

        // gamemode command messages
        config.addDefault("gamemode", "");
        config.addDefault("gamemode.permission.self", "elemental.gamemode");
        config.addDefault("gamemode.permission.others", "elemental.gamemode.others");
        config.addDefault("gamemode.message.survival", "&eGamemode changed to: &bSurvival");
        config.addDefault("gamemode.message.creative", "&eGamemode changed to: &bCreative");
        config.addDefault("gamemode.message.adventure", "&eGamemode changed to: &bAdventure");
        config.addDefault("gamemode.message.spectator", "&eGamemode changed to: &bSpectator");

        // vanish command messages
        config.addDefault("vanish", "");
        config.addDefault("vanish.permission.self", "elemental.vanish");
        config.addDefault("vanish.permission.others", "elemental.vanish.others");
        config.addDefault("vanish.permission.bypass", "elemental.vanish.bypass");
        config.addDefault("vanish.message.self.on", "&eYou are now invisible to other players!");
        config.addDefault("vanish.message.self.off", "&eYou are now visible to all players!");
        config.addDefault("vanish.message.others.on", "&c%player% &eis now invisible to all players!");
        config.addDefault("vanish.message.others.off", "&c%player% &eis now visible to all players!");

        // godmode command messages
        config.addDefault("godmode", "");
        config.addDefault("godmode.permission.self", "elemental.godmode");
        config.addDefault("godmode.permission.others", "elemental.godmode.others");
        config.addDefault("godmode.message.self.on", "&eYou are now invulnerable!");
        config.addDefault("godmode.message.self.off", "&eYou are no longer invulnerable!");
        config.addDefault("godmode.message.others.on", "&c%player% &eis now invulnerable!");
        config.addDefault("godmode.message.others.off", "&c%player% &eis no longer invulnerable!");

        // heal command messages
        config.addDefault("heal", "");
        config.addDefault("heal.permission.self", "elemental.heal");
        config.addDefault("heal.permission.others", "elemental.heal.others");
        config.addDefault("heal.message.self", "&eYou have been healed!");
        config.addDefault("heal.message.others", "&c%player% &ehas been healed!");

        // nick command messages
        config.addDefault("nick", "");
        config.addDefault("nick.permission.self", "elemental.nick");
        config.addDefault("nick.permission.others", "elemental.nick.others");
        config.addDefault("nick.message.self.reset", "&eYour nick has been reseted!");
        config.addDefault("nick.message.self.set", "&eYour name was set to &c%player%&e!");
        config.addDefault("nick.message.others.reset", "&eThe name of &c%player% &ehas been reseted!");
        config.addDefault("nick.message.others.set", "&eThe name of &c%oplayer% &ewas set to &c%player%&e!");

        // resetnick command messages
        config.addDefault("resetnick", "");
        config.addDefault("resetnick.permission.self", "elemental.nick");
        config.addDefault("resetnick.permission.others", "elemental.nick.others");
        config.addDefault("resetnick.message.self", "&eYour nick has been reseted!");
        config.addDefault("resetnick.message.others", "&eThe name of &c%player% &ehas been reseted!");

        // sethealth command messages
        config.addDefault("sethealth", "");
        config.addDefault("sethealth.permission.self", "elemental.sethealth");
        config.addDefault("sethealth.permission.others", "elemental.sethealth.others");
        config.addDefault("sethealth.message.self", "&eYour healtscale has been set to &c%healthScale%&e!");
        config.addDefault("sethealth.message.others", "&eThe healtscale of &c%player% &ehas been set to &c%healthScale%&e!");

        // uuid command messages
        config.addDefault("uuid", "");
        config.addDefault("uuid.permission", "elemental.uuid");
        config.addDefault("uuid.message", "&eThe UUID of &c%player% &eis &c%uuid%");

    }

}
