/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import java.util.List;

// Utility class for creating ItemStacks with custom properties
public class ItemBuilder {
    // Creates an ItemStack with the specified material and name
    public static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

            item.setItemMeta(meta);
        }

        return item;
    }

    // Creates an ItemStack with the specified material, name and lore
    public static ItemStack createItemWithLore(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

            List<String> loreList = Collections.singletonList(ChatColor.GRAY + lore);
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }

        return item;
    }

    // Creates a player head ItemStack with a custom name and owner
    public static ItemStack createSkull(String name, Player owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setOwningPlayer(owner);

            List<String> loreList = Collections.singletonList("Ping: " + owner.getPing());
            meta.setLore(loreList);
            skull.setItemMeta(meta);
        }

        return skull;
    }

    // Creates a bow ItemStack with a custom name and lore
    public static ItemStack createBow(String name, String lore) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + name);
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.INFINITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

            List<String> loreList = Collections.singletonList(ChatColor.GRAY + lore);
            meta.setLore(loreList);
            bow.setItemMeta(meta);
        }

        return bow;
    }
}