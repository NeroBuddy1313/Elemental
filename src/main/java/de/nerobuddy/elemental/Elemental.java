package de.nerobuddy.elemental;

import de.nerobuddy.elemental.commands.*;
import de.nerobuddy.elemental.listeners.PlayerJoinListener;
import de.nerobuddy.elemental.listeners.PlayerMoveListener;
import de.nerobuddy.elemental.listeners.PlayerQuitListener;
import de.nerobuddy.elemental.utils.CommandPool;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.nerobuddy.elemental.utils.Utils.setConfigDefaults;

public final class Elemental extends JavaPlugin {

    private static Elemental plugin;
    private CommandPool commandPool;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        commandPool = new CommandPool();

        // Config
        FileConfiguration config = plugin.getConfig();
        setConfigDefaults();

        // Config defaults
        config.options().copyDefaults(true);
        saveConfig();

        // register Commands
        registerCommands();

        // register Listeners
        registerListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        commandPool.addCommand(new FlyCommand());
        commandPool.addCommand(new GameModeCommand());
        commandPool.addCommand(new FeedCommand());
        commandPool.addCommand(new HealCommand());
        commandPool.addCommand(new SetHealthCommand());
        commandPool.addCommand(new GodModeCommand());
        commandPool.addCommand(new UuidCommand());
        commandPool.addCommand(new NickCommand());
        commandPool.addCommand(new ResetNickCommand());
        commandPool.addCommand(new IsAFKCommand());
        commandPool.addCommand(new AFKCommand());
    }

    public void registerListeners() {
        PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new PlayerJoinListener(), this);
        pM.registerEvents(new PlayerQuitListener(), this);
        pM.registerEvents(new PlayerMoveListener(), this);
    }

    public static Elemental getPlugin() {
        return plugin;
    }
}
