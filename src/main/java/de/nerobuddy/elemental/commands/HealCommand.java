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
    public void executePlayerCommand(Player p, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission("elemental.heal") || p.hasPermission("elemental.heal.others"))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (p.hasPermission("elemental.heal")) {
                p.setFoodLevel(20);
                p.setHealth(p.getMaxHealth());
                msgPlayer(p, color(prefix + "&eYou have been healed!"));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission("elemental.heal.others")) {
                Player t = Bukkit.getPlayer(args[0]);
                if(t != null) {
                    t.setFoodLevel(20);
                    t.setHealth(t.getMaxHealth());
                    msgPlayer(t, color(prefix + "&eYou have been healed!"));
                    msgPlayer(p, color(prefix + "&c" + t.getName() + " &ehas been healed!"));
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