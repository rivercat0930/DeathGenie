package com.gmail.h90093000054.DeathGenie.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int exp_level = player.getLevel();

        if (exp_level >= 10) {
            event.setKeepInventory(true);
            event.getDrops().clear();

            event.setNewLevel(exp_level - 10);
            event.setDroppedExp(0);
        }
    }
}
