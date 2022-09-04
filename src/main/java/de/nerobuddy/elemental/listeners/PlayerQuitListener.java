package de.nerobuddy.elemental.listeners;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.AFKManager.playerLeft;
import static de.nerobuddy.elemental.utils.Utils.color;

/**
 * @author m_wei
 * @project Elemental
 * @created 02.09.2022 - 21:12
 */

public class PlayerQuitListener implements Listener {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        // AFK player left
        playerLeft(uuid);

        // set quit message
        e.setQuitMessage(color(prefix + Objects.requireNonNull(config.getString("quitMessage")).replace("%player%", e.getPlayer().getDisplayName())));
    }

}
