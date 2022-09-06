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

import static de.nerobuddy.elemental.utils.AFKManager.isAFK;
import static de.nerobuddy.elemental.utils.Utils.color;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:33
 */

public class IsAFKCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "isafk";
    }

    @Override
    public String getUsage() {
        return "/isafk <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!p.hasPermission(Objects.requireNonNull(config.getString("isafk.permission")))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (isAFK(p.getUniqueId())) {
                msgPlayer(p, color(prefix + config.getString("isafk.message.self.afk")));
            } else {
                msgPlayer(p, color(prefix + config.getString("isafk.message.self.noafk")));
            }
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayerExact(args[0]);
            if (t != null) {
                if (isAFK(t.getUniqueId())) {
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("isafk.message.others.afk")).replace("%player%", t.getDisplayName())));
                } else {
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("isafk.message.others.noafk")).replace("%player%", t.getDisplayName())));
                }
            } else {
                throw new PlayerNotFoundException(args[0]);
            }
        } else {
            throw new InvalidUsageException();
        }
    }
}
