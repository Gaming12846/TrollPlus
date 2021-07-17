package com.github.gaming12846.trollplus.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils ItemBuilder.java
 *
 * @author Gaming12846
 */
public final class ItemBuilder {

    // Create an itemstack
    public static ItemStack createItem(Material mat, int amount, int subId, String name) {
        ItemStack item = new ItemStack(mat, 1, (short) subId);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Create an itemstack with lore
    public static ItemStack createItemLore(Material mat, int amount, int subId, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat, amount, (short) subId);
        ItemMeta meta = item.getItemMeta();
        final List<String> formatted = new ArrayList<String>();
        for (String str : lore) {
            formatted.add(ChatColor.WHITE + str);
        }
        meta.setLore(formatted);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Create an skull
    public static ItemStack createSkull(int amount, int subId, String name, String owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) subId);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName(name);
        skull.setItemMeta(meta);
        return skull;
    }

}