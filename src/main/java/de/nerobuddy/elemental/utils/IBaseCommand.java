package de.nerobuddy.elemental.utils;

import org.bukkit.command.CommandExecutor;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 22:42
 */

public interface IBaseCommand extends CommandExecutor {

    /**
     * @return Command name
     */

    String getName();

}
