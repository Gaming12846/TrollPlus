/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ControlUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final TrollPlus plugin;

    public PlayerJoinListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
            ControlUtil controlUtil = plugin.getInventoryClickListener().controlUtil;

            player.setInvulnerable(false);
            player.getInventory().setContents(controlUtil.getPlayerInventory());
            player.getInventory().setArmorContents(controlUtil.getPlayerArmor());
            player.getInventory().setItemInOffHand(controlUtil.getPlayerOffHandItem());
            player.setLevel(controlUtil.getPlayerLevel());
            player.setExp(controlUtil.getPlayerExp());
            if (plugin.getConfig().getBoolean("control-teleport-back", true))
                player.teleport(controlUtil.getPlayerLocation());
            player.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
        }
    }
}