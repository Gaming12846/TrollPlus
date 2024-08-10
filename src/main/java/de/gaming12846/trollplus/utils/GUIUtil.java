/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

// Utility class for creating and managing graphical user interfaces (GUIs)
public class GUIUtil {
    private final Inventory inventory;
    private Player player;
    private ConfigUtil langConfig;

    // Constructor for the GUIUtil for a general GUI
    public GUIUtil(String title, Integer size) {
        inventory = Bukkit.createInventory(null, size, title);
    }

    // Constructor for the GUIUtil for the Troll GUI
    public GUIUtil(String title, Integer size, Player target, TrollPlus plugin) {
        inventory = Bukkit.createInventory(null, size, title);
        player = target;
        langConfig = plugin.getLanguageConfig();
    }

    // Constructor for the GUIUtil for the TrollPlus settings GUI
    public GUIUtil(String title, Integer size, TrollPlus plugin) {
        inventory = Bukkit.createInventory(null, size, title);
        langConfig = plugin.getLanguageConfig();
    }

    // Adds an item to the specified index in the GUI
    public void addItem(Integer index, ItemStack item) {
        inventory.setItem(index, item);
    }

    // Adds an item with custom name and lore to the specified index in the GUI
    public void addItemWithLore(Integer index, Material material, String name, String lore) {
        inventory.setItem(index, ItemBuilder.createItemWithLore(material, name, lore));
    }

    // Retrieves the Inventory (GUI)
    public Inventory getGUI() {
        return inventory;
    }

    // Retrieves the GUIUtil instance
    public GUIUtil getGUIUtil() {
        return this;
    }

    // Retrieves the target player
    public Player getTarget() {
        return player;
    }

    // Retrieves the status string for the troll GUI
    public String getStatusTrollGUI(String metadata) {
        if (player.hasMetadata(metadata)) return "§a§l" + langConfig.getString("troll-gui.status-on");
        return "§c§l" + langConfig.getString("troll-gui.status-off");
    }

    // Retrieves the status string for the settings GUI
    public String getStatusSettingsGUI(Boolean configboolean) {
        if (configboolean) return "§a§l" + langConfig.getString("trollsettings.status-on");
        return "§c§l" + langConfig.getString("trollsettings.status-off");
    }
}