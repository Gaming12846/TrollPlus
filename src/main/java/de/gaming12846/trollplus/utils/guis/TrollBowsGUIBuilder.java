/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils.guis;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrollBowsGUIBuilder {
    private final Inventory inv;

    public TrollBowsGUIBuilder(String name, Integer size) {
        inv = Bukkit.createInventory(null, size, name);
    }

    public void addItem(Integer index, ItemStack item) {
        inv.setItem(index, item);
    }

    public Inventory getGUI() {
        return inv;
    }
}