package com.gmail.h90093000054.DeathGenie.event;

import com.gmail.h90093000054.DeathGenie.DeathGenie;
import com.gmail.h90093000054.DeathGenie.file.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeath implements Listener {
    private Plugin plugin = DeathGenie.getMainPlugin();
    private DataManager data = new DataManager((DeathGenie) plugin);

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int times = 0;

        if (data.getDataConfig().contains("players." + player.getUniqueId().toString() + "." + player.getName() + ".times")) {
            data.reloadConfig();
            times = data.getDataConfig().getInt("players." + player.getUniqueId().toString() + "." + player.getName() + ".times");
        }

        if (times >= 1) {
            event.setKeepInventory(true);
            event.getDrops().clear();

            event.setKeepLevel(true);
            event.setDroppedExp(0);

            times--;
        }

        data.getDataConfig().set("players." + player.getUniqueId().toString() + "." + player.getName() + ".times", times);
        data.saveDataConfig();
    }
}
