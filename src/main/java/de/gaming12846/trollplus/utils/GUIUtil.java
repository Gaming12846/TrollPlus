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

public class GUIUtil {
    private final Inventory inv;
    private Player player;
    private ConfigUtil langConfig;

    // GUIUtil for Trollbows
    public GUIUtil(String name, Integer size) {
        inv = Bukkit.createInventory(null, size, name);
    }

    // GUIUtil for Troll
    public GUIUtil(String name, Integer size, Player target, TrollPlus plugin) {
        inv = Bukkit.createInventory(null, size, name);
        player = target;
        langConfig = plugin.getLanguageConfig();
    }

    // GUIUtil for TrollPlus settings
    public GUIUtil(String name, Integer size, TrollPlus plugin) {
        inv = Bukkit.createInventory(null, size, name);
        langConfig = plugin.getLanguageConfig();
    }

    public void addItem(Integer index, ItemStack item) {
        inv.setItem(index, item);
    }

    public void addItemWithLore(Integer index, Material material, String name, String lore) {
        inv.setItem(index, ItemBuilder.createItemWithLore(material, name, lore));
    }

    public Inventory getGUI() {
        return inv;
    }

    public GUIUtil getGUIUtil() {
        return this;
    }

    public Player getTarget() {
        return player;
    }

    public String getStatus(String metadata) {
        if (player.hasMetadata(metadata)) return "§a§l" + langConfig.getString("troll-gui.status-on");
        return "§c§l" + langConfig.getString("troll-gui.status-off");
    }

    public String getConfigStatus(Boolean configboolean) {
        if (configboolean) return "§a§l" + langConfig.getString("trollsettings.status-on");
        return "§c§l" + langConfig.getString("trollsettings.status-off");
    }
}