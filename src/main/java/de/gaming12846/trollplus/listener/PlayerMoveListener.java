/*
 *
 *  * This file is part of TrollPlus.
 *  * Copyright (C) 2023 Gaming12846
 *
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);
    }
}