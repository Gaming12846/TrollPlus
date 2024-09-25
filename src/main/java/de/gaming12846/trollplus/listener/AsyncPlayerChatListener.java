/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import de.gaming12846.trollplus.utils.ControlHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

// Listener for handling player chat events asynchronously
public class AsyncPlayerChatListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the AsyncPlayerChatListener
    public AsyncPlayerChatListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the AsyncPlayerChatEvent
    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ControlHelper controlHelper = plugin.getInventoryClickListener().getControlHelper();

        // Handle players being controlled by another player
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET))
            // Cancel the chat event if control message boolean is false
            if (!controlHelper.getControlMessageBoolean()) event.setCancelled(true);

        // Handle the player who is controlling another player
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER)) {
            // Cancel the chat event and relay the message through ControlHelper
            event.setCancelled(true);
            controlHelper.setControlMessage(event.getMessage());
            controlHelper.setControlMessageBoolean(true);
        }

        // Handle players who are semi-banned
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN)) {
            // Cancel the chat event and replace it with a custom message
            event.setCancelled(true);
            String semiBanMessageReplace = plugin.getConfigHelper().getString(ConfigConstants.SEMI_BAN_MESSAGE_REPLACE);

            // Send the replacement message if it's configured
            if (!semiBanMessageReplace.isEmpty())
                player.sendMessage(semiBanMessageReplace.replace("[player]", player.getName()) + " " + event.getMessage());
        }
    }
}