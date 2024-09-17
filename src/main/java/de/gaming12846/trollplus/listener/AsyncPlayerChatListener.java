/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ControlUtil;
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
        ControlUtil controlUtil = plugin.getInventoryClickListener().getControlUtil();

        // Handle players being controlled by another player
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET"))
            // Cancel the chat event if control message boolean is false
            if (!controlUtil.getControlMessageBoolean()) event.setCancelled(true);

        // Handle the player who is controlling another player
        if (player.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
            // Cancel the chat event and relay the message through ControlUtil
            event.setCancelled(true);
            controlUtil.setControlMessage(event.getMessage());
            controlUtil.setControlMessageBoolean(true);
        }

        // Handle players who are semi-banned
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            // Cancel the chat event and replace it with a custom message
            event.setCancelled(true);
            String semiBanMessageReplace = plugin.getConfigHelper().getString("semi-ban-message-replace");

            // Send the replacement message if it's configured
            if (!semiBanMessageReplace.isEmpty())
                player.sendMessage(semiBanMessageReplace.replace("[player]", player.getName()) + " " + event.getMessage());
        }
    }
}