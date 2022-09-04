package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.nerobuddy.elemental.utils.Utils.color;

/**
 * @author m_wei
 * @project Elemental
 * @created 02.09.2022 - 20:56
 */

public class GodModeCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    private final List<UUID> godModeList = new ArrayList<>();

    @Override
    public String getName() {
        return "godmode";
    }

    @Override
    public String getUsage() {
        return "/godmode <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission("elemental.godmode") || p.hasPermission("elemental.godmode.others"))) {
            throw new NoPermissionException();
        }
        UUID uuid;
        if (args.length == 0) {
            uuid = p.getUniqueId();
            if (p.hasPermission("elemental.godmode")) {
                if (godModeList.contains(uuid)) {
                    p.setInvulnerable(false);
                    godModeList.remove(uuid);
                    p.sendMessage(color(prefix + "&eYou are no longer invulnerable!"));
                } else {
                    p.setInvulnerable(true);
                    godModeList.add(uuid);
                    p.sendMessage(color(prefix + "&eYou are now invulnerable!"));
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission("elemental.godmode.others")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    uuid = t.getUniqueId();
                    if (godModeList.contains(uuid)) {
                        t.setInvulnerable(false);
                        godModeList.remove(uuid);
                        t.sendMessage(color(prefix + "&eYou are no longer invulnerable!"));
                        p.sendMessage(color(prefix + "&c" + t.getDisplayName() + " &eis no longer invulnerable!"));
                    } else {
                        t.setInvulnerable(true);
                        godModeList.add(uuid);
                        t.sendMessage(color(prefix + "&eYou are now invulnerable!"));
                        p.sendMessage(color(prefix + "&c" + t.getDisplayName() + " &eis now invulnerable!"));
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
