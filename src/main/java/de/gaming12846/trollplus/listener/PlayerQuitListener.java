/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
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
        if (plugin.getConfigHelper().getBoolean(ConfigConstants.DEACTIVATE_FEATURES_ON_QUIT)) {
            // Remove player metadata related to troll features
            removeMetadata(player);

            // Ensure the player is visible to all online players
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.showPlayer(plugin, player);
            }

            // Remove any active slowness potion effects
            if (plugin.getServerVersion() > 1.19) player.removePotionEffect(PotionEffectType.SLOWNESS);
        }
    }

    // Removes metadata related to various troll features from the player
    private void removeMetadata(Player player) {
        String[] metadataKeys = {MetadataConstants.TROLLPLUS_VANISH, MetadataConstants.TROLLPLUS_FREEZE, MetadataConstants.TROLLPLUS_HAND_ITEM_DROP, MetadataConstants.TROLLPLUS_CONTROL_TARGET, MetadataConstants.TROLLPLUS_CONTROL_PLAYER, MetadataConstants.TROLLPLUS_FLIP_BEHIND, MetadataConstants.TROLLPLUS_SPANK, MetadataConstants.TROLLPLUS_SPAM_MESSAGES, MetadataConstants.TROLLPLUS_SPAM_SOUNDS, MetadataConstants.TROLLPLUS_SEMI_BAN, MetadataConstants.TROLLPLUS_FALLING_ANVILS, MetadataConstants.TROLLPLUS_TNT_TRACK, MetadataConstants.TROLLPLUS_MOB_SPAWNER, MetadataConstants.TROLLPLUS_SLOWLY_KILL, MetadataConstants.TROLLPLUS_ROCKET_NO_FALL_DAMAGE};

        for (String key : metadataKeys) {
            player.removeMetadata(key, plugin);
        }
    }
}