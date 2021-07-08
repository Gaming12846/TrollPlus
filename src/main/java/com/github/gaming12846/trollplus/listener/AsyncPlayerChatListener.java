package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener AsyncPlayerChatListener.java
 *
 * @author Gaming12846
 */
public final class AsyncPlayerChatListener implements Listener {

    private final TrollPlus plugin;

    public AsyncPlayerChatListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            if (VMConstants.CONTROL_MESSAGE_BOOLEAN == false) {
                event.setCancelled(true);
            }
        }

        if (player.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
            event.setCancelled(true);
            VMConstants.CONTROL_MESSAGE = event.getMessage();
            VMConstants.CONTROL_MESSAGE_BOOLEAN = true;
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);

            String semiBanMessageReplace = plugin.getConfig().getString(VMConstants.CONFIG_SEMI_BAN_MESSAGE_REPLACE, "");
            if (semiBanMessageReplace == null) {
                semiBanMessageReplace = "";
            }

            if (!semiBanMessageReplace.isEmpty()) {
                player.sendMessage(semiBanMessageReplace.replace("[PLAYER]", player.getName()) + " " + event.getMessage());
            }
        }

        return;
    }

}