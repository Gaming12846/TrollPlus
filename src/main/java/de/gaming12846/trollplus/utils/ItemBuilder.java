/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {
    // Create an ItemStack with lore
    public static ItemStack createItemWithLore(Material mat, String name, String lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);

        List<String> lore2 = Collections.singletonList(lore);
        final List<String> formatted = new ArrayList<>();
        for (String string : lore2) {
            formatted.add(ChatColor.GRAY + string);
        }

        meta.setLore(formatted);
        item.setItemMeta(meta);

        return item;
    }

    // Create a skull
    public static ItemStack createSkull(String name, Player owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.setOwningPlayer(owner);
        skull.setItemMeta(meta);

        return skull;
    }

    // Create a bow with lore
    public static ItemStack createBow(String name, String lore) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RED + name);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        List<String> lore2 = Collections.singletonList(lore);
        final List<String> formatted = new ArrayList<>();
        for (String string : lore2) {
            formatted.add(ChatColor.GRAY + string);
        }

        meta.setLore(formatted);
        bow.setItemMeta(meta);

        return bow;
    }
}