/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrollGUIBuilder {
    private final Inventory inv;
    private final Player player;

    public TrollGUIBuilder(String name, Integer size, Player target) {
        inv = Bukkit.createInventory(null, size, name);
        player = target;
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
        return player;
    }

    public String getStatus(String metadata) {
        if (player.hasMetadata(metadata)) return "§a§lON";
        return "§c§lOFF";
    }
}