package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.Utils.msgPlayer;
import static de.nerobuddy.elemental.utils.VanishManager.setVanished;

/**
 * @author m_wei
 * @project Elemental
 * @created 05.09.2022 - 10:57
 */

public class VanishCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");


    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public String getUsage() {
        return "/vanish <player>";
    }

    @Override
    public void executePlayerCommand(Player p, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("vanish.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("vanish.permission.others"))))) {
            throw new NoPermissionException();
        }
        UUID uuid;
        if (args.length == 0) {
            uuid = p.getUniqueId();
            if (p.hasPermission(Objects.requireNonNull(config.getString("vanish.permission.self")))) {
                if (setVanished(uuid)) {
                    msgPlayer(p, prefix + config.getString("vanish.message.self.on"));
                } else {
                    msgPlayer(p, prefix + config.getString("vanish.message.self.off"));
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("vanish.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    uuid = t.getUniqueId();
                    if (setVanished(uuid)) {
                        msgPlayer(t, prefix + config.getString("vanish.message.self.on"));
                        msgPlayer(p, prefix + Objects.requireNonNull(config.getString("vanish.message.others.on")).replace("%player%", t.getDisplayName()));
                    } else {
                        msgPlayer(t, prefix + config.getString("vanish.message.self.off"));
                        msgPlayer(p, prefix + Objects.requireNonNull(config.getString("vanish.message.others.off")).replace("%player%", t.getDisplayName()));
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
