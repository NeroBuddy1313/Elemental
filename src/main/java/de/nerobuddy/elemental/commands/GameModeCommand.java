package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

import static de.nerobuddy.elemental.utils.Utils.color;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 23:17
 */

public class GameModeCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "gamemode";
    }

    @Override
    public String getUsage() {
        return "/gamemode <survival, creative, adventure, spectator>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!p.hasPermission(Objects.requireNonNull(config.getString("gamemode.permission.self"))) || !p.hasPermission(Objects.requireNonNull(config.getString("gamemode.permission.others")))) {
            throw new NoPermissionException();
        }
        if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("gamemode.permission.self")))) {
                if (changeGameMode(p, args)) {
                    throw new InvalidUsageException();
                }
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 2) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("gamemode.permission.others")))) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (changeGameMode(p, args)) {
                        throw new InvalidUsageException();
                    }
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

    public boolean changeGameMode(final Player p, final String[] args) {
        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
            p.setGameMode(GameMode.SURVIVAL);
            msgPlayer(p, color(prefix + config.getString("gamemode.message.survival")));
        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
            p.setGameMode(GameMode.CREATIVE);
            msgPlayer(p, color(prefix + config.getString("gamemode.message.creative")));
        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
            p.setGameMode(GameMode.ADVENTURE);
            msgPlayer(p, color(prefix + config.getString("gamemode.message.adventure")));
        } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
            p.setGameMode(GameMode.SPECTATOR);
            msgPlayer(p, color(prefix + config.getString("gamemode.message.spectator")));
        } else {
            return true;
        }
        return false;
    }

}
