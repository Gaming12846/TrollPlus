/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);
    }
}