/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerQuitListener implements Listener {
    private final TrollPlus plugin;

    public PlayerQuitListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("deactivate-features-on-quit", true)) {
            player.removeMetadata("TROLLPLUS_VANISH", plugin);
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.showPlayer(plugin, player);
            }
            player.removeMetadata("TROLLPLUS_FREEZE", plugin);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);
            player.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
            player.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);
            player.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);
            player.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);
            player.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);
            player.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);
            player.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);
            player.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
        }
    }
}