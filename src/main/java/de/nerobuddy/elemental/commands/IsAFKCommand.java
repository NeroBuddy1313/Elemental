package de.nerobuddy.elemental.commands;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import de.nerobuddy.elemental.utils.PlayerCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static de.nerobuddy.elemental.utils.AFKManager.isAFK;
import static de.nerobuddy.elemental.utils.Utils.color;
import static de.nerobuddy.elemental.utils.Utils.msgPlayer;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:33
 */

public class IsAFKCommand extends PlayerCommandHandler {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");

    @Override
    public String getName() {
        return "isafk";
    }

    @Override
    public String getUsage() {
        return "/isafk <player>";
    }

    @Override
    public void executePlayerCommand(Player p, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException {
        if(!p.hasPermission("elemental.isafk")){
            throw new NoPermissionException();
        }
        if(args.length == 0){
            if (isAFK(p.getUniqueId())) {
                msgPlayer(p, color(prefix + "&eYou are currently AFK!"));
            }else{
                msgPlayer(p, color(prefix + "&eYou are currently not AFK!"));
            }
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayerExact(args[0]);
            if(t != null) {
                if (isAFK(t.getUniqueId())) {
                    msgPlayer(p, color(prefix + "&c" + t.getDisplayName() + " &eis currently AFK!"));
                }else{
                    msgPlayer(p, color(prefix + "&c" + t.getDisplayName() + " &eis currently not AFK!"));
                }
            }else {
                throw  new PlayerNotFoundException(args[0]);
            }
        } else {
            throw new InvalidUsageException();
        }
    }
}
