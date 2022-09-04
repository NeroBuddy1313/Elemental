package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.AFKManager;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.Utils.broadcastMessageWithoutOwner;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:59
 */

public class AFKCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "afk";
    }

    @Override
    public String getUsage() {
        return "/afk";
    }

    @Override
    public void executePlayerCommand(Player p, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("afk.permission.others"))) || p.hasPermission(Objects.requireNonNull(config.getString("afk.permission.self"))))) {
            throw new NoPermissionException();
        }
        UUID uuid;
        if (args.length == 0) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("afk.permission.self")))) {
                uuid = p.getUniqueId();
                if (AFKManager.toggleAFKStatus(uuid)) {
                    msgPlayer(p, prefix + config.getString("afk.message.self.afk"));
                    broadcastMessageWithoutOwner(p, prefix + Objects.requireNonNull(config.getString("afk.message.others.afk")).replace("%player%", p.getDisplayName()));
                } else {
                    msgPlayer(p, prefix + config.getString("afk.message.self.noafk"));
                    broadcastMessageWithoutOwner(p, prefix + Objects.requireNonNull(config.getString("afk.message.others.noafk")).replace("%player%", p.getDisplayName()));
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("afk.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    uuid = t.getUniqueId();
                    if (AFKManager.toggleAFKStatus(uuid)) {
                        msgPlayer(t, prefix + config.getString("afk.message.self.afk"));
                        broadcastMessageWithoutOwner(t, prefix + Objects.requireNonNull(config.getString("afk.message.others.afk")).replace("%player%", t.getDisplayName()));
                    } else {
                        msgPlayer(t, prefix + config.getString("afk.message.self.noafk"));
                        broadcastMessageWithoutOwner(t, prefix + Objects.requireNonNull(config.getString("afk.message.others.noafk")).replace("%player%", t.getDisplayName()));
                    }
                } else {
                    throw new PlayerNotFoundException(args[0]);
                }
            } else {
                throw new NoPermissionException();
            }
        } else {
            throw new InvalidUsageException();
        }
    }
}
