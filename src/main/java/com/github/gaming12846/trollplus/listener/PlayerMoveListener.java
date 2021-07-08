package com.github.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener PlayerMoveListener.java
 *
 * @author Gaming12846
 */
public final class PlayerMoveListener implements Listener {

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) {
            event.setCancelled(true);
        }

        return;
    }

}