/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final TrollPlus plugin;

    public PlayerDeathListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET"))
            plugin.getInventoryClickListener().controlUtil.getPlayer().getInventory().clear();

        // Feature kill
        if (player.hasMetadata("TROLLPLUS_KILL")) {
            player. removeMetadata("TROLLPLUS_KILL", plugin);
            event.setDeathMessage(player.getName() + " " + plugin.getLanguageConfig().getString("troll.kill-message"));
        }
    }
}