package de.nerobuddy.elemental.listeners;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

import static de.nerobuddy.elemental.utils.Utils.color;

/**
 * @author m_wei
 * @project Elemental
 * @created 02.09.2022 - 21:09
 */

public class PlayerJoinListener implements Listener {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(color(prefix + Objects.requireNonNull(config.getString("joinMessage")).replace("%player%", e.getPlayer().getName())));
    }

}
