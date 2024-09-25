/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.LangConstants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

// Utility class for creating and managing graphical user interfaces (GUIs)
public class GUIHelper {
    private final Inventory inventory;
    private Player player;
    private ConfigHelper configHelperLanguage;

    // Constructor for the GUIHelper for a general GUI
    public GUIHelper(String title, Integer size) {
        inventory = Bukkit.createInventory(null, size, title);
    }

    // Constructor for the GUIHelper for the Troll GUI
    public GUIHelper(String title, Integer size, Player target, TrollPlus plugin) {
        inventory = Bukkit.createInventory(null, size, title);
        player = target;
        configHelperLanguage = plugin.getConfigHelperLanguage();
    }

    // Constructor for the GUIHelper for the TrollPlus settings GUI
    public GUIHelper(String title, Integer size, TrollPlus plugin) {
        inventory = Bukkit.createInventory(null, size, title);
        configHelperLanguage = plugin.getConfigHelperLanguage();
    }

    // Adds an item to the specified index in the GUI
    public void addItem(Integer index, ItemStack item) {
        inventory.setItem(index, item);
    }

    // Adds an item with custom name and lore to the specified index in the GUI
    public void addItemWithLore(Integer index, Material material, String name, String lore) {
        inventory.setItem(index, ItemBuilder.createItemWithLore(material, name, lore));
    }

    // Adds an item with custom name, lore and status to the specified index in the GUI
    public void addItemWithLoreAndStatus(Integer index, Material material, String name, String metadata, String lore) {
        inventory.setItem(index, ItemBuilder.createItemWithLore(material, name + " " + getStatus(metadata), lore));
    }

    // Retrieves the Inventory (GUI)
    public Inventory getGUI() {
        return inventory;
    }

    // Retrieves the GUIHelper instance
    public GUIHelper getGUIUtil() {
        return this;
    }

    // Retrieves the target player
    public Player getTarget() {
        return player;
    }

    // Retrieves the status string
    public String getStatus(String metadata) {
        if (player.hasMetadata(metadata)) return "§a§l" + configHelperLanguage.getString(LangConstants.GUI_STATUS_ON);
        return "§c§l" + configHelperLanguage.getString(LangConstants.GUI_STATUS_OFF);
    }

    // Retrieves the status string
    public String getStatus(Boolean configboolean) {
        if (configboolean) return "§a§l" + configHelperLanguage.getString(LangConstants.GUI_STATUS_ON);
        return "§c§l" + configHelperLanguage.getString(LangConstants.GUI_STATUS_OFF);
    }
}