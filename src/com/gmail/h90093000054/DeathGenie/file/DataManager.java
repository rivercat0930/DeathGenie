package com.gmail.h90093000054.DeathGenie.file;

import com.gmail.h90093000054.DeathGenie.DeathGenie;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {
    private DeathGenie plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public DataManager(DeathGenie plugin) {
        this.plugin = plugin;

        // save initializes config
        saveDefualtConfig();
    }

    public void reloadConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), "data.yml");

        dataConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource("data.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getDataConfig() {
        if (dataConfig == null)
            reloadConfig();

        return dataConfig;
    }

    public void saveDataConfig() {
        if (dataConfig == null || configFile == null)
            return;

        try {
            getDataConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }

    public void saveDefualtConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), "data.yml");

        if (!configFile.exists())
            plugin.saveResource("data.yml", false);
    }
}
