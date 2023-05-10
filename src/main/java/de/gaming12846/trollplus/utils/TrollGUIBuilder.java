/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrollGUIBuilder {
    private final Inventory inv;
    private Player target;

    public TrollGUIBuilder(String name, Integer size) {
        inv = Bukkit.createInventory(null, size, name);

        target = null;
    }

    public void addItem(Integer index, ItemStack item) {
        inv.setItem(index, item);
    }


    public Inventory getGUI() {
        return inv;
    }

    public TrollGUIBuilder getGUIBuilder() {
        return this;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player player) {
        target = player;
    }

    public String getStatus(String metadata) {
        if (target.hasMetadata(metadata)) return "§a§lON";
        return "§c§lOFF";
    }
}