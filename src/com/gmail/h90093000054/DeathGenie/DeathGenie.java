package com.gmail.h90093000054.DeathGenie;

import com.gmail.h90093000054.DeathGenie.command.OpenGuiCommand;
import com.gmail.h90093000054.DeathGenie.event.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathGenie extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.GREEN +
                        "\n=========================" +
                        "DeathGenie is online" +
                        "=========================\n");

        plugin = this;

        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

        getServer().getPluginManager().registerEvents(new Gui(), this);
        getServer().getPluginCommand("dg").setExecutor(new OpenGuiCommand());
    }

    @Override
    public void onDisable() {
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}
