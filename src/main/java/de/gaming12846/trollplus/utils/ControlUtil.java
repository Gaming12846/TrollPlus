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
    private final int playerLevel;
    private final float playerExp;
    private final TrollPlus plugin;
    private boolean controlMessageBoolean;
    private String controlMessage;

    public ControlUtil(TrollPlus plugin, Player target, Player player) {
        this.plugin = plugin;

        playerTarget = target;
        playerPlayer = player;

        controlMessageBoolean = false;
        controlMessage = null;

        playerLocation = player.getLocation();
        playerInventory = player.getInventory().getContents();
        playerArmor = player.getInventory().getArmorContents();
        playerOffHandItem = player.getInventory().getItemInOffHand();
        playerLevel = player.getLevel();
        playerExp = player.getExp();
    }

    public Player getPlayer() {
        return playerPlayer;
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

    public int getPlayerLevel() {
        return playerLevel;
    }

    public float getPlayerExp() {
        return playerExp;
    }

    public boolean getControlMessageBoolean() {
        return controlMessageBoolean;
    }

    public void setControlMessageBoolean(boolean bool) {
        controlMessageBoolean = bool;
    }

    public void setControlMessage(String message) {
        controlMessage = message;
    }

    public void control() {
        boolean allowFlight = true;

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(plugin, playerPlayer);
        }

        playerPlayer.hidePlayer(plugin, playerTarget);
        playerPlayer.setInvulnerable(true);

        playerPlayer.teleport(playerTarget);
        playerPlayer.getInventory().setContents(playerTarget.getInventory().getContents());
        playerPlayer.getInventory().setArmorContents(playerTarget.getInventory().getArmorContents());
        playerPlayer.getInventory().setItemInOffHand(playerTarget.getInventory().getItemInOffHand());
        playerPlayer.setLevel(playerTarget.getLevel());
        playerPlayer.setExp(playerTarget.getExp());

        if (!playerTarget.getAllowFlight()) {
            allowFlight = false;
            playerTarget.setAllowFlight(true);
        }

        boolean finalAllowFlight = allowFlight;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playerTarget.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                    playerPlayer.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
                    playerPlayer.setInvulnerable(false);
                    playerPlayer.getInventory().setContents(playerInventory);
                    playerPlayer.getInventory().setArmorContents(playerArmor);
                    playerPlayer.getInventory().setItemInOffHand(playerOffHandItem);
                    playerPlayer.setLevel(playerLevel);
                    playerPlayer.setExp(playerExp);
                    if (plugin.getConfig().getBoolean("control-teleport-back", true))
                        playerPlayer.teleport(playerLocation);

                    if (!finalAllowFlight) playerTarget.setAllowFlight(false);

                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.showPlayer(plugin, playerPlayer);
                    }

                    playerPlayer.showPlayer(plugin, playerTarget);

                    cancel();
                    return;
                } else if (!playerPlayer.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
                    playerTarget.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);

                    cancel();
                    return;
                }

                if (controlMessageBoolean) {
                    playerTarget.chat(controlMessage);
                    controlMessageBoolean = false;
                }

                if (playerTarget.getLocation() != playerPlayer.getLocation()) playerTarget.teleport(playerPlayer);
                if (!playerPlayer.getInventory().equals(playerTarget.getInventory()) || !Arrays.equals(playerPlayer.getInventory().getArmorContents(), playerTarget.getInventory().getArmorContents()) || !playerPlayer.getInventory().getItemInOffHand().equals(playerTarget.getInventory().getItemInOffHand())) {
                    playerTarget.getInventory().setContents(playerPlayer.getInventory().getContents());
                    playerTarget.getInventory().setArmorContents(playerPlayer.getInventory().getArmorContents());
                    playerTarget.getInventory().setItemInOffHand(playerPlayer.getInventory().getItemInOffHand());
                }
                if (playerTarget.getLevel() != playerPlayer.getLevel()) playerTarget.setLevel(playerPlayer.getLevel());
                if (playerTarget.getExp() != playerPlayer.getExp()) playerTarget.setExp(playerPlayer.getExp());
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}