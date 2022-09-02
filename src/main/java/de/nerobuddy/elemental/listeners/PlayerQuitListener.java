package de.nerobuddy.elemental.listeners;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

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
        e.setQuitMessage(color(prefix + Objects.requireNonNull(config.getString("quitMessage")).replace("%player%", e.getPlayer().getName())));
    }

}
