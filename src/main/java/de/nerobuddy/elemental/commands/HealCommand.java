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
 * @created 01.09.2022 - 23:27
 */

public class HealCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "heal";
    }

    @Override
    public String getUsage() {
        return "/heal <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("heal.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("heal.permission.others"))))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("heal.permission.self")))) {
                p.setFoodLevel(20);
                p.setHealth(p.getMaxHealth());
                msgPlayer(p, color(prefix + config.getString("heal.message.self")));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("heal.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    t.setFoodLevel(20);
                    t.setHealth(t.getMaxHealth());
                    msgPlayer(t, color(prefix + config.getString("heal.message.self")));
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("heal.message.others")).replace("%player%", t.getDisplayName())));
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
