package de.nerobuddy.elemental.listeners;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.AFKManager.playerJoined;
import static de.nerobuddy.elemental.utils.NickManager.getNickNames;
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
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        // set nickname from before session
        if(getNickNames().containsKey(uuid)){
            p.setDisplayName(getNickNames().get(uuid));
        }

        // AFK player joined
        playerJoined(uuid);

        // set join message
        e.setJoinMessage(color(prefix + Objects.requireNonNull(config.getString("joinMessage")).replace("%player%", p.getDisplayName())));
    }

}
