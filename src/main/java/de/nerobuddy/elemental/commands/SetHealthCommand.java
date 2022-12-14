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
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 23:30
 */

public class SetHealthCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "sethealth";
    }

    @Override
    public String getUsage() {
        return "/sethealth <amount> <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("sethealth.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("sethealth.permission.others"))))) {
            throw new NoPermissionException();
        }
        int healthScale;
        try {
            healthScale = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidUsageException();
        }
        if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("sethealth.permission.self")))) {
                p.setHealthScale(healthScale);
                msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("sethealth.message.self")).replace("%healthScale%", String.valueOf(healthScale))));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 2) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("sethealth.permission.others")))) {
                Player t = Bukkit.getPlayer(args[1]);
                if (t != null) {
                    t.setHealthScale(healthScale);
                    msgPlayer(t, color(prefix + Objects.requireNonNull(config.getString("sethealth.message.self")).replace("%healthScale%", String.valueOf(healthScale))));
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("sethealth.message.others")).replace("%healthScale%", String.valueOf(healthScale)).replace("%player%", t.getDisplayName())));
                } else {
                    throw new PlayerNotFoundException(args[1]);
                }
            } else {
                throw new NoPermissionException();
            }
        } else {
            throw new InvalidUsageException();
        }
    }

}
