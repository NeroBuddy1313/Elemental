package de.nerobuddy.elemental.utils;

import de.nerobuddy.elemental.Elemental;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 22:40
 */

public class CommandPool implements CommandExecutor {

    private final Set<IBaseCommand> commandSet = new HashSet<>();

    public CommandPool() {
    }

    public void addCommand(final IBaseCommand command) {
        commandSet.add(command);
        Objects.requireNonNull(Elemental.getPlugin().getCommand(command.getName())).setExecutor(this);
    }

    public void removeCommand(final IBaseCommand command) {
        commandSet.removeIf(baseCommand -> baseCommand.getName().equals(command.getName()));
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        for (IBaseCommand command1 : commandSet) {
            if (label.equals(command1.getName())) {
                return command1.onCommand(sender, command, label, args);
            }
        }
        return false;
    }
}
