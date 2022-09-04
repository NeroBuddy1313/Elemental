package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
        if (!(p.hasPermission("elemental.sethealth") || p.hasPermission("elemental.sethealth.others"))) {
            throw new NoPermissionException();
        }
        int healthScale;
        try {
            healthScale = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidUsageException();
        }
        if (args.length == 1) {
            if (p.hasPermission("elemental.sethealth")) {
                p.setHealthScale(healthScale);
                msgPlayer(p, color(prefix + "&eYour healtscale has been set to &c" + healthScale + "&e!"));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 2) {
            if (p.hasPermission("elemental.sethealth.others")) {
                Player t = Bukkit.getPlayer(args[1]);
                if (t != null) {
                    t.setHealthScale(healthScale);
                    msgPlayer(t, color(prefix + "&eYour healtscale has been set to &c" + healthScale + "&e!"));
                    msgPlayer(p, color(prefix + "&eThe healtscale of &c" + t.getDisplayName() + " &ehas been set to &c" + healthScale + "&e!"));
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
