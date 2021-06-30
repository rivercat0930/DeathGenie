package com.gmail.h90093000054.DeathGenie;

import com.gmail.h90093000054.DeathGenie.event.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathGenie extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.GREEN +
                        "=========================" +
                        "DeathGenie is online" +
                        "=========================");
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
    }

    @Override
    public void onDisable() {
    }
}
