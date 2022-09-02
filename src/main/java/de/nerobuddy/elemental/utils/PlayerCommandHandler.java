package de.nerobuddy.elemental.utils;

import de.nerobuddy.elemental.Elemental;
import de.nerobuddy.elemental.exceptions.InvalidUsageException;
import de.nerobuddy.elemental.exceptions.NoPermissionException;
import de.nerobuddy.elemental.exceptions.PlayerNotFoundException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.nerobuddy.elemental.utils.Utils.color;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 22:50
 */

public abstract class PlayerCommandHandler implements IBaseCommand {

    private final Elemental plugin = Elemental.getPlugin();
    private final FileConfiguration config = plugin.getConfig();
    private final String prefix = config.getString("prefix");


    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color(prefix + "&cThis command could only be executed by players!"));
        } else {
            try {
                executePlayerCommand((Player) sender, args);
            } catch (NoPermissionException e) {
                sender.sendMessage(color(prefix + "&cYou do not have permission to use this command!"));
            } catch (InvalidUsageException e) {
                sender.sendMessage(color(prefix + "&eUsage " + getUsage()));
            } catch (PlayerNotFoundException e) {
                sender.sendMessage(color(prefix + "&eThe player &c" + e.getPlayerName() + " &ecould not be found!"));
            }
        }
        return false;
    }

    public abstract String getUsage();

    public abstract void executePlayerCommand(Player sender, String[] args) throws NoPermissionException, InvalidUsageException, PlayerNotFoundException;

}
