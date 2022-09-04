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

import static de.nerobuddy.elemental.utils.Utils.color;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 23:02
 */

public class FlyCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "fly";
    }

    @Override
    public String getUsage() {
        return "/fly <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("fly.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("fly.permission.others"))))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("fly.permission.self")))) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(color(prefix + config.getString("fly.message.self.nofly")));
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(color(prefix + config.getString("fly.message.self.fly")));
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("fly.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    if (t.getAllowFlight()) {
                        t.setAllowFlight(false);
                        t.setFlying(false);
                        t.sendMessage(color(prefix + config.getString("fly.message.self.nofly")));
                        p.sendMessage(color(prefix + Objects.requireNonNull(config.getString("fly.message.others.nofly")).replace("%player%", t.getDisplayName())));
                    } else {
                        t.setAllowFlight(true);
                        t.setFlying(true);
                        t.sendMessage(color(prefix + config.getString("fly.message.self.fly")));
                        p.sendMessage(color(prefix + Objects.requireNonNull(config.getString("fly.message.others.fly")).replace("%player%", t.getDisplayName())));
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
