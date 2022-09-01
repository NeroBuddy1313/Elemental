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
    public void executePlayerCommand(Player p, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission("elemental.fly") || p.hasPermission("elemental.fly.others"))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (p.hasPermission("elemental.fly")) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(color(prefix + "&eYou can't fly anymore!"));
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(color(prefix + "&eYou can fly now!"));
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission("elemental.fly.others")) {
                Player t = Bukkit.getPlayer(args[0]);
                if(t != null) {
                    if (t.getAllowFlight()) {
                        t.setAllowFlight(false);
                        t.setFlying(false);
                        t.sendMessage(color(prefix + "&eYou can't fly anymore!"));
                        p.sendMessage(color(prefix + "&c" + t.getDisplayName() + " &ecan't fly anymore!"));
                    } else {
                        t.setAllowFlight(true);
                        t.setFlying(true);
                        t.sendMessage(color(prefix + "§eYou can fly now!"));
                        p.sendMessage(color(prefix + "&c" + t.getDisplayName() + " &ecan fly now!"));
                    }
                }else{
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
