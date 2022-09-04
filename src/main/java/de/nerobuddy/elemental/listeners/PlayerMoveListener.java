package de.nerobuddy.elemental.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static de.nerobuddy.elemental.utils.AFKManager.playerMoved;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:58
 */

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e) {
        // AFK player moved
        playerMoved(e.getPlayer().getUniqueId());
    }

}
