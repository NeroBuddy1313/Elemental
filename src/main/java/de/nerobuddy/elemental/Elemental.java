package de.nerobuddy.elemental;

import de.nerobuddy.elemental.commands.*;
import de.nerobuddy.elemental.utils.CommandPool;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
        config.addDefault("prefix", "&8[&eElemental&8] ");
        config.addDefault("joinMessage", "&c%player% &ehas joined the server");
        config.addDefault("quitMessage", "&c%player% &ehas left the server");

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
    }

    public void registerListeners() {
        PluginManager pM = Bukkit.getPluginManager();
    }

    public static Elemental getPlugin() {
        return plugin;
    }
}
