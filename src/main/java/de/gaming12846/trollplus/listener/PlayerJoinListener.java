/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import de.gaming12846.trollplus.utils.ControlUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// Listener for handling player join events
public class PlayerJoinListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the PlayerJoinListener
    public PlayerJoinListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the PlayerJoinEvent
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if the player has the "TROLLPLUS_CONTROL_PLAYER" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER)) {
            ControlUtil controlUtil = plugin.getInventoryClickListener().getControlUtil();

            // Return the player's own items and levels
            player.setInvulnerable(false);
            player.getInventory().setContents(controlUtil.getPlayerInventory());
            player.getInventory().setArmorContents(controlUtil.getPlayerArmor());
            player.getInventory().setItemInOffHand(controlUtil.getPlayerOffHandItem());
            player.setLevel(controlUtil.getPlayerLevel());
            player.setExp(controlUtil.getPlayerExp());

            // Teleport the player back to their previous location if configured
            if (plugin.getConfigHelper().getBoolean(ConfigConstants.CONTROL_TELEPORT_BACK))
                player.teleport(controlUtil.getPlayerLocation());

            // Remove the "TROLLPLUS_CONTROL_PLAYER" metadata
            player.removeMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER, plugin);
        }
    }
}