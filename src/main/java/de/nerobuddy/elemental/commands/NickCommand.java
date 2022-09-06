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

import static de.nerobuddy.elemental.utils.NickManager.addPlayerNick;
import static de.nerobuddy.elemental.utils.NickManager.removePlayerNick;
import static de.nerobuddy.elemental.utils.Utils.color;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 18:00
 */

public class NickCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "nick";
    }

    @Override
    public String getUsage() {
        return "/nick <nickname>";
    }

    @Override
    public void executePlayerCommand(final Player p, final String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if (!(p.hasPermission(Objects.requireNonNull(config.getString("nick.permission.self"))) || p.hasPermission(Objects.requireNonNull(config.getString("nick.permission.others"))))) {
            throw new NoPermissionException();
        }
        String nickName;
        if (args.length == 1) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("nick.permission.self")))) {
                if (args[0].equals("reset")) {
                    p.setDisplayName(p.getName());
                    removePlayerNick(p.getUniqueId());
                    msgPlayer(p, color(prefix + config.getString("nick.message.self.reset")));
                    return;
                }
                nickName = color(args[0]);
                p.setDisplayName(nickName);
                addPlayerNick(p.getUniqueId(), nickName);
                msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("nick.message.self.set")).replace("%player%", p.getDisplayName())));
            } else {
                throw new NoPermissionException();
            }
        } else if (args.length == 2) {
            if (p.hasPermission(Objects.requireNonNull(config.getString("nick.permission.self")))) {
                if (args[1].equals("reset")) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        t.setDisplayName(t.getName());
                        removePlayerNick(t.getUniqueId());
                        msgPlayer(t, color(prefix + config.getString("nick.message.self.reset")));
                        msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("nick.message.others.reset")).replace("%player%", p.getDisplayName())));
                    } else {
                        throw new PlayerNotFoundException(args[0]);
                    }
                }
            } else if (p.hasPermission(Objects.requireNonNull(config.getString("nick.permission.others")))) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    nickName = args[1];
                    t.setDisplayName(color(nickName));
                    addPlayerNick(t.getUniqueId(), nickName);
                    msgPlayer(t, color(prefix + Objects.requireNonNull(config.getString("nick.message.self.set")).replace("%player%", t.getDisplayName())));
                    msgPlayer(p, color(prefix + Objects.requireNonNull(config.getString("nick.message.others.set")).replace("%player%", t.getDisplayName()).replace("%oplayer%", t.getName())));
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
