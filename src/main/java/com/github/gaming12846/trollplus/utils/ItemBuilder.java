package com.github.gaming12846.trollplus.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
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
public class ItemBuilder {

    // Create an itemstack with lore
    public static ItemStack createItemWithLore(Material mat, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        final List<String> formatted = new ArrayList<>();
        for (String string : lore) {
            formatted.add(ChatColor.GRAY + string);
        }
        meta.setLore(formatted);
        item.setItemMeta(meta);
        return item;
    }

    // Create a skull
    public static ItemStack createSkull(int amount, String name, Player owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.setOwningPlayer(owner);
        skull.setItemMeta(meta);
        return skull;
    }

    // Create a bow
    public static ItemStack createBow(int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.BOW, amount);
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