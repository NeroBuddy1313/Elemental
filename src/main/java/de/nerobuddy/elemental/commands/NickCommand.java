package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
        if (!(p.hasPermission("elemental.nick"))) {
            throw new NoPermissionException();
        }
        String nickName;
        if (args.length == 1) {
            if (args[0].equals("reset")) {
                p.setDisplayName(p.getName());
                removePlayerNick(p.getUniqueId());
                msgPlayer(p, color(prefix + "&eYour nick has been reseted!"));
                return;
            }
            nickName = color(args[0]);
            p.setDisplayName(nickName);
            addPlayerNick(p.getUniqueId(), nickName);
            msgPlayer(p, color(prefix + "&eYour name was set to &c" + p.getDisplayName() + "&e!"));
        } else if (args.length == 2) {
            if (args[1].equals("reset")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    t.setDisplayName(t.getName());
                    removePlayerNick(t.getUniqueId());
                    msgPlayer(t, color(prefix + "&eYour nick has been reseted!"));
                    msgPlayer(t, color(prefix + "&eThe name of &c" + t.getDisplayName() + " &ehas been reseted!"));
                    return;
                }else {
                    throw new PlayerNotFoundException(args[0]);
                }
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (t != null) {
                nickName = args[1];
                t.setDisplayName(color(nickName));
                addPlayerNick(t.getUniqueId(), nickName);
                msgPlayer(t, color(prefix + "&eYour name was set to &c" + t.getDisplayName() + "&e!"));
                msgPlayer(p, color(prefix + "&eThe name of &c" + t.getName() + " &ewas set to &c" + p.getDisplayName() + "&e!"));
            }else {
                throw new PlayerNotFoundException(args[0]);
            }
        } else {
            throw new InvalidUsageException();
        }
    }

}
