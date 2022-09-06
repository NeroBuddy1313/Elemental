package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 17:33
 */

public class UuidCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "uuid";
    }

    @Override
    public String getUsage() {
        return "/uuid <name>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("uuid.permission"))))) {
            throw new NoPermissionException();
        }
        if (args.length != 1) {
            throw new InvalidUsageException();
        }
        OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
        msgPlayer(p, prefix + Objects.requireNonNull(config.getString("uuid.message")).replace("%player%", Objects.requireNonNull(t.getName())).replace("%uuid%", String.valueOf(t.getUniqueId())));
    }
}
