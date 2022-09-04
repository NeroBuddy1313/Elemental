package de.nerobuddy.elemental.utils;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.Utils.*;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:30
 */

public class AFKManager {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    private static final long MOVEMENT_THRESHOLD = 60000;

    private static final Map<UUID, Long> lastMovement = new HashMap<>();

    public static void playerJoined(UUID uuid) {
        lastMovement.put(uuid, System.currentTimeMillis());
    }

    public static void playerLeft(UUID uuid) {
        lastMovement.remove(uuid);
    }

    public static boolean toggleAFKStatus(UUID uuid) {
        if (isAFK(uuid)) {
            lastMovement.put(uuid, System.currentTimeMillis());
            return false;
        } else {
            lastMovement.put(uuid, -1L);
            return true;
        }
    }

    public static void playerMoved(UUID uuid) {
        boolean wasAFK = isAFK(uuid);
        lastMovement.put(uuid, System.currentTimeMillis());
        boolean nowAFK = isAFK(uuid);

        AFKManager afkManager = new AFKManager();
        Player p = Bukkit.getPlayer(uuid);
        if (wasAFK && !nowAFK) {
            msgPlayer(p, color(afkManager.prefix + afkManager.config.getString("afk.message.self.noafk")));

            broadcastMessageWithoutOwner(p, afkManager.prefix + Objects.requireNonNull(afkManager.config.getString("afk.message.others.noafk")).replace("%player%", p.getDisplayName()));

        } else if (!wasAFK && nowAFK) {
            msgPlayer(p, color(afkManager.prefix + afkManager.config.getString("afk.message.self.afk")));

            broadcastMessageWithoutOwner(p, afkManager.prefix + Objects.requireNonNull(afkManager.config.getString("afk.message.others.afk")).replace("%player%", p.getDisplayName()));

        }

    }

    public static boolean isAFK(UUID uuid) {
        if (lastMovement.get(uuid) == -1L) {
            return true;
        } else {
            long timeElapsed = System.currentTimeMillis() - lastMovement.get(uuid);
            return timeElapsed >= MOVEMENT_THRESHOLD;
        }
    }

}
