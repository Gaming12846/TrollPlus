/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

// Utility class for handling player control mechanics in the TrollPlus plugin
public class ControlHelper {
    private final Player playerTarget;               // The player being controlled
    private final Player playerController;           // The player controlling the target
    private final Location playerLocation;           // The original location of the controlling player
    private final ItemStack[] playerInventory;       // Original inventory of the controlling player
    private final ItemStack[] playerArmor;           // Original armor of the controlling player
    private final ItemStack playerOffHandItem;       // Original off-hand item of the controlling player
    private final int playerLevel;                   // Original level of the controlling player
    private final float playerExp;                   // Original experience of the controlling player
    private final TrollPlus plugin;                  // Reference to the main plugin
    private boolean controlMessageEnabled;           // Flag to check if control messages are enabled
    private String controlMessage;                   // Control message to send

    // Constructor for the ControlHelper
    public ControlHelper(TrollPlus plugin, Player target, Player controller) {
        this.plugin = plugin;
        this.playerTarget = target;
        this.playerController = controller;

        // Initialize control message settings
        this.controlMessageEnabled = false;
        this.controlMessage = null;

        // Save original player state
        this.playerLocation = controller.getLocation();
        this.playerInventory = controller.getInventory().getContents();
        this.playerArmor = controller.getInventory().getArmorContents();
        this.playerOffHandItem = controller.getInventory().getItemInOffHand();
        this.playerLevel = controller.getLevel();
        this.playerExp = controller.getExp();
    }

    // Starts the control process, where the controller takes over the target player's state
    public void control() {
        boolean wasFlightAllowed = playerTarget.getAllowFlight();
        playerTarget.setAllowFlight(true);

        // Hide the controlling player from everyone and the target player
        hidePlayers();

        // Teleport the controlling player to the target player's location and copy their state
        teleportAndCopyState();

        // Schedule the control task
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isControlActive()) {
                    endControl(wasFlightAllowed);
                    cancel();
                    return;
                }

                if (controlMessageEnabled) {
                    playerTarget.chat(controlMessage);
                    controlMessageEnabled = false;
                }

                synchronizePlayerStates();
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getLong(ConfigConstants.CONTROL_PERIOD));
    }

    // Ends the control process, restoring the controlling player's original state and visibility
    private void endControl(boolean wasFlightAllowed) {
        restorePlayerState();
        restoreVisibility();

        // Teleport back if needed
        if (plugin.getConfigHelper().getBoolean(ConfigConstants.CONTROL_TELEPORT_BACK)) {
            playerController.teleport(playerLocation);
        }

        // Reset flight permission if it was not allowed before
        if (!wasFlightAllowed) {
            playerTarget.setAllowFlight(false);
        }
    }

    // Hides the controlling player from other players and the target player
    private void hidePlayers() {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(plugin, playerController);
        }
        playerController.hidePlayer(plugin, playerTarget);
        playerController.setInvulnerable(true);
    }

    // Teleports the controlling player to the target and copies the target's state
    private void teleportAndCopyState() {
        playerController.teleport(playerTarget);
        playerController.getInventory().setContents(playerTarget.getInventory().getContents());
        playerController.getInventory().setArmorContents(playerTarget.getInventory().getArmorContents());
        playerController.getInventory().setItemInOffHand(playerTarget.getInventory().getItemInOffHand());
        playerController.setLevel(playerTarget.getLevel());
        playerController.setExp(playerTarget.getExp());
    }

    // Restores the original state of the controlling player
    private void restorePlayerState() {
        playerController.removeMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER, plugin);
        playerController.setInvulnerable(false);
        playerController.getInventory().setContents(playerInventory);
        playerController.getInventory().setArmorContents(playerArmor);
        playerController.getInventory().setItemInOffHand(playerOffHandItem);
        playerController.setLevel(playerLevel);
        playerController.setExp(playerExp);
    }

    // Restores the visibility of the controlling player to others
    private void restoreVisibility() {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.showPlayer(plugin, playerController);
        }
        playerController.showPlayer(plugin, playerTarget);
    }

    // Checks if the control is still active
    private boolean isControlActive() {
        return playerTarget.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET) && playerController.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER);
    }

    // Synchronizes the target player's state with the controlling player's state
    private void synchronizePlayerStates() {
        // Teleport target to controller's location if they are not equal
        if (!playerTarget.getLocation().equals(playerController.getLocation())) {
            playerTarget.teleport(playerController.getLocation());
        }

        // Update inventory, armor, and off-hand item if they differ
        if (!Arrays.equals(playerController.getInventory().getContents(), playerTarget.getInventory().getContents())) {
            playerTarget.getInventory().setContents(playerController.getInventory().getContents());
        }

        if (!Arrays.equals(playerController.getInventory().getArmorContents(), playerTarget.getInventory().getArmorContents())) {
            playerTarget.getInventory().setArmorContents(playerController.getInventory().getArmorContents());
        }

        if (!playerController.getInventory().getItemInOffHand().equals(playerTarget.getInventory().getItemInOffHand())) {
            playerTarget.getInventory().setItemInOffHand(playerController.getInventory().getItemInOffHand());
        }

        // Update level and experience if they differ
        if (playerTarget.getLevel() != playerController.getLevel()) {
            playerTarget.setLevel(playerController.getLevel());
        }

        if (playerTarget.getExp() != playerController.getExp()) {
            playerTarget.setExp(playerController.getExp());
        }
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
    public void setControlMessageBoolean(boolean enabled) {
        this.controlMessageEnabled = enabled;
    }

    // Sets the control message to be sent
    public void setControlMessage(String message) {
        this.controlMessage = message;
    }
}