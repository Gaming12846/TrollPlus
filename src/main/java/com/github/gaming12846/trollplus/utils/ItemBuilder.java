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

    // Create an itemstack with lore
    public static ItemStack createItemWithLore(Material mat, int amount, int subId, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat, amount, (short) subId);
        ItemMeta meta = item.getItemMeta();
        final List<String> formatted = new ArrayList<>();
        for (String string : lore) {
            formatted.add(ChatColor.GRAY + string);
        }
        assert meta != null;
        meta.setLore(formatted);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Create a skull
    public static ItemStack createSkull(int amount, int subId, String name, String owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) subId);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        meta.setOwner(owner);
        meta.setDisplayName(name);
        skull.setItemMeta(meta);
        return skull;
    }

    // Create a bow
    public static ItemStack createBow(int amount, int subId, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.BOW, amount, (short) subId);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RED + name);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        final List<String> formatted = new ArrayList<>();
        for (String string : lore) {
            formatted.add(ChatColor.GRAY + string);
        }
        meta.setLore(formatted);
        item.setItemMeta(meta);
        return item;
    }

}