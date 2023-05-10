/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class ControlUtil {
    private final Player playerTarget;
    private final Player playerPlayer;
    private final Location playerLocation;
    private final ItemStack[] playerInventory;
    private final ItemStack[] playerArmor;
    private final ItemStack playerOffHandItem;
    private final TrollPlus plugin;
    private String controlMessage;

    public ControlUtil(TrollPlus plugin, Player target, Player player) {
        this.plugin = plugin;

        playerTarget = target;
        playerPlayer = player;

        controlMessage = null;

        playerLocation = player.getLocation();
        playerInventory = player.getInventory().getContents();
        playerArmor = player.getInventory().getArmorContents();
        playerOffHandItem = player.getInventory().getItemInOffHand();
    }

    public String getControlMessage() {
        return controlMessage;
    }

    public void setControlMessage(String message) {
        controlMessage = message;
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    public ItemStack[] getPlayerInventory() {
        return playerInventory;
    }

    public ItemStack[] getPlayerArmor() {
        return playerArmor;
    }

    public ItemStack getPlayerOffHandItem() {
        return playerOffHandItem;
    }

    public void control() {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(plugin, playerPlayer);
        }

        playerPlayer.hidePlayer(plugin, playerTarget);

        playerPlayer.teleport(playerTarget);
        playerPlayer.getInventory().setContents(playerTarget.getInventory().getContents());
        playerPlayer.getInventory().setArmorContents(playerTarget.getInventory().getArmorContents());
        playerPlayer.getInventory().setItemInOffHand(playerTarget.getInventory().getItemInOffHand());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playerTarget.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {

                    playerPlayer.getInventory().setContents(playerInventory);
                    playerPlayer.getInventory().setArmorContents(playerArmor);
                    playerPlayer.getInventory().setItemInOffHand(playerOffHandItem);
                    if (plugin.getConfig().getBoolean("control-teleport-back", true))
                        playerPlayer.teleport(playerLocation);

                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.showPlayer(plugin, playerPlayer);
                    }

                    playerPlayer.showPlayer(plugin, playerTarget);

                    cancel();
                    return;
                } else if (!playerPlayer.isOnline()) {
                    playerTarget.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);

                    cancel();
                    return;
                }

                if (controlMessage != null) {
                    playerTarget.chat(controlMessage);
                    controlMessage = null;
                }

                if (playerTarget.getLocation() != playerPlayer.getLocation()) playerTarget.teleport(playerPlayer);
                if (!playerPlayer.getInventory().equals(playerTarget.getInventory()) || !Arrays.equals(playerPlayer.getInventory().getArmorContents(), playerTarget.getInventory().getArmorContents()) || !playerPlayer.getInventory().getItemInOffHand().equals(playerTarget.getInventory().getItemInOffHand())) {
                    playerTarget.getInventory().setContents(playerPlayer.getInventory().getContents());
                    playerTarget.getInventory().setArmorContents(playerPlayer.getInventory().getArmorContents());
                    playerTarget.getInventory().setItemInOffHand(playerPlayer.getInventory().getItemInOffHand());
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}