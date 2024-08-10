/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

// Listener for handling player quit events
public class PlayerQuitListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the PlayerQuitListener
    public PlayerQuitListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the PlayerQuitEvent
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Check if features should be deactivated upon player quit
        if (plugin.getConfig().getBoolean("deactivate-features-on-quit", true)) {
            // Remove player metadata related to troll features
            removeMetadata(player);

            // Ensure the player is visible to all online players
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.showPlayer(plugin, player);
            }

            // Remove any active slowness potion effects
            player.removePotionEffect(PotionEffectType.SLOWNESS);
        }
    }

    // Removes metadata related to various troll features from the player
    private void removeMetadata(Player player) {
        String[] metadataKeys = {"TROLLPLUS_VANISH", "TROLLPLUS_FREEZE", "TROLLPLUS_HAND_ITEM_DROP", "TROLLPLUS_CONTROL_TARGET", "TROLLPLUS_CONTROL_PLAYER", "TROLLPLUS_FLIP_BEHIND", "TROLLPLUS_SPANK", "TROLLPLUS_SPAM_MESSAGES", "TROLLPLUS_SPAM_SOUNDS", "TROLLPLUS_SEMI_BAN", "TROLLPLUS_FALLING_ANVILS", "TROLLPLUS_TNT_TRACK", "TROLLPLUS_MOB_SPAWNER", "TROLLPLUS_SLOWLY_KILL", "TROLLPLUS_ROCKET_NO_FALL_DAMAGE"};

        for (String key : metadataKeys) {
            player.removeMetadata(key, plugin);
        }
    }
}