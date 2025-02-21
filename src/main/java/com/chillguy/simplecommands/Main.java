package com.chillguy.simplecommands;

import com.chillguy.simplecommands.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        loadCommands();
    }

    @Override
    public void onDisable() {

    }

    void loadCommands() {
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("openinv").setExecutor(new OpenInvCommand());
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("trash").setExecutor(new TrashCommand());
    }

    public static Main getInstance() {
        return instance;
    }
}
