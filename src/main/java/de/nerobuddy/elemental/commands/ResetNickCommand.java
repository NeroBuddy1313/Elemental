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

import static de.nerobuddy.elemental.utils.NickManager.removePlayerNick;
import static de.nerobuddy.elemental.utils.Utils.color;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:00
 */

public class ResetNickCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "resetnick";
    }

    @Override
    public String getUsage() {
        return "/resetnick <player>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("resetnick.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("resetnick.permission.others"))))) {
            throw new NoPermissionException();
        }
        if (args.length == 0) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("resetnick.permission.self")))) {
                p.setDisplayName(p.getName());
                removePlayerNick(p.getUniqueId());
                msgPlayer(p, color(prefix + config.getString("resetnick.message.self")));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("resetnick.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("resetnick.message.others")).replace("%player%", t.getDisplayName())));
                    t.setDisplayName(t.getName());
                    removePlayerNick(t.getUniqueId());
                    msgPlayer(t, color(prefix + config.getString("resetnick.message.self")));
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
