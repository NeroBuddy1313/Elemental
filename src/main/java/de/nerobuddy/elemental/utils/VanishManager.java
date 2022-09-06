package de.nerobuddy.elemental.utils;


import de.nerobuddy.elemental.Elemental;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author m_wei
 * @project Elemental
 * @created 05.09.2022 - 10:29
 */

public class VanishManager {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private static final List<UUID> VANISHED = new ArrayList<>();

    public List<UUID> getVanished() {
        return VANISHED;
    }

    public static boolean isVanished(final UUID uuid) {
        return VANISHED.contains(uuid);
    }

    public static boolean setVanished(final UUID uuid) {
        VanishManager vanishManager = new VanishManager();
        if (VANISHED.contains(uuid)) {
            VANISHED.remove(uuid);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(vanishManager.plugin, Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
        } else {
            VANISHED.add(uuid);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(Objects.requireNonNull(vanishManager.config.getString("vanish.permission.bypass")))) {
                    onlinePlayer.hidePlayer(vanishManager.plugin, Objects.requireNonNull(Bukkit.getPlayer(uuid)));
                }
            }
        }
        return isVanished(uuid);

    }

    public static void hideAll(UUID uuid) {
        VanishManager vanishManager = new VanishManager();
        for (UUID uuid1 : VANISHED) {
            if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission(Objects.requireNonNull(vanishManager.config.getString("elemental.permission.bypass"))))
                continue;
            Objects.requireNonNull(Bukkit.getPlayer(uuid)).hidePlayer(vanishManager.plugin, Objects.requireNonNull(Bukkit.getPlayer(uuid1)));
        }
    }

}
