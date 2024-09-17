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

// Utility class for handling player control mechanics in the TrollPlus plugin
public class ControlUtil {
    private final Player playerTarget;
    private final Player playerController;
    private final Location playerLocation;
    private final ItemStack[] playerInventory;
    private final ItemStack[] playerArmor;
    private final ItemStack playerOffHandItem;
    private final int playerLevel;
    private final float playerExp;
    private final TrollPlus plugin;
    private boolean controlMessageEnabled;
    private String controlMessage;

    // Constructor for the ControlUtil
    public ControlUtil(TrollPlus plugin, Player target, Player player) {
        this.plugin = plugin;
        playerTarget = target;
        playerController = player;

        controlMessageEnabled = false;
        controlMessage = null;

        playerLocation = player.getLocation();
        playerInventory = player.getInventory().getContents();
        playerArmor = player.getInventory().getArmorContents();
        playerOffHandItem = player.getInventory().getItemInOffHand();
        playerLevel = player.getLevel();
        playerExp = player.getExp();
    }

    // Retrieves the player being controlled
    public Player getPlayer() {
        return playerController;
    }

    // Retrieves the initial location of the controlling player
    public Location getPlayerLocation() {
        return playerLocation;
    }

    // Retrieves the initial inventory contents of the controlling player
    public ItemStack[] getPlayerInventory() {
        return playerInventory;
    }

    // Retrieves the initial armor contents of the controlling player
    public ItemStack[] getPlayerArmor() {
        return playerArmor;
    }

    // Retrieves the initial item in the off-hand of the controlling player
    public ItemStack getPlayerOffHandItem() {
        return playerOffHandItem;
    }

    // Retrieves the initial level of the controlling player
    public int getPlayerLevel() {
        return playerLevel;
    }

    // Retrieves the initial experience of the controlling player
    public float getPlayerExp() {
        return playerExp;
    }

    // Checks if control messages are enabled
    public boolean getControlMessageBoolean() {
        return controlMessageEnabled;
    }

    // Sets whether control messages should be enabled
    public void setControlMessageBoolean(boolean bool) {
        controlMessageEnabled = bool;
    }

    // Sets the control message to be sent
    public void setControlMessage(String message) {
        controlMessage = message;
    }

    // Starts the control process, where the controller takes over the target player's state
    public void control() {
        boolean wasFlightAllowed = playerTarget.getAllowFlight();
        playerTarget.setAllowFlight(true);

        // Hide the controlling player from everyone and the target player
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(plugin, playerController);
        }
        playerController.hidePlayer(plugin, playerTarget);
        playerController.setInvulnerable(true);

        // Teleport the controlling player to the target player's location and copy their state
        playerController.teleport(playerTarget);
        playerController.getInventory().setContents(playerTarget.getInventory().getContents());
        playerController.getInventory().setArmorContents(playerTarget.getInventory().getArmorContents());
        playerController.getInventory().setItemInOffHand(playerTarget.getInventory().getItemInOffHand());
        playerController.setLevel(playerTarget.getLevel());
        playerController.setExp(playerTarget.getExp());

        // Set up a periodic task to manage the control state
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playerTarget.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                    endControl(wasFlightAllowed);
                    cancel();
                    return;
                } else if (!playerController.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
                    playerTarget.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
                    cancel();
                    return;
                }

                if (controlMessageEnabled) {
                    playerTarget.chat(controlMessage);
                    controlMessageEnabled = false;
                }

                synchronizePlayerStates();
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getLong("control.period"));
    }

    // Ends the control process, restoring the controlling player's original state and visibility
    private void endControl(boolean wasFlightAllowed) {
        playerController.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
        playerController.setInvulnerable(false);
        playerController.getInventory().setContents(playerInventory);
        playerController.getInventory().setArmorContents(playerArmor);
        playerController.getInventory().setItemInOffHand(playerOffHandItem);
        playerController.setLevel(playerLevel);
        playerController.setExp(playerExp);

        if (plugin.getConfigHelper().getBoolean("control.teleport-back")) playerController.teleport(playerLocation);

        if (!wasFlightAllowed) {
            playerTarget.setAllowFlight(false);
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.showPlayer(plugin, playerController);
        }
        playerController.showPlayer(plugin, playerTarget);
    }

    // Synchronizes the target player's state with the controlling player's state
    private void synchronizePlayerStates() {
        if (!playerTarget.getLocation().equals(playerController.getLocation()))
            playerTarget.teleport(playerController.getLocation());

        if (!Arrays.equals(playerController.getInventory().getContents(), playerTarget.getInventory().getContents()) || !Arrays.equals(playerController.getInventory().getArmorContents(), playerTarget.getInventory().getArmorContents()) || !playerController.getInventory().getItemInOffHand().equals(playerTarget.getInventory().getItemInOffHand())) {
            playerTarget.getInventory().setContents(playerController.getInventory().getContents());
            playerTarget.getInventory().setArmorContents(playerController.getInventory().getArmorContents());
            playerTarget.getInventory().setItemInOffHand(playerController.getInventory().getItemInOffHand());
        }

        if (playerTarget.getLevel() != playerController.getLevel()) playerTarget.setLevel(playerController.getLevel());

        if (playerTarget.getExp() != playerController.getExp()) playerTarget.setExp(playerController.getExp());
    }
}