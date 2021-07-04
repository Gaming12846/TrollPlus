package com.github.gaming12846.trollplus.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils ItemBuilder.java
 *
 * @author Gaming12846
 */
public class ItemBuilder {
	// Easy method to create an itemstack
	public static ItemStack createItem(Material mat, int subid, String name) {
		ItemStack item = new ItemStack(mat, 1, (short) subid);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	// Easy method to create an itemstack with lore
	public static ItemStack createItemLore(Material mat, int subid, String name, List<String> lore) {
		ItemStack item = new ItemStack(mat, 1, (short) subid);
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

	// Easy method to create an skull
	public static ItemStack createSkull(int subid, String name, String owner) {
		ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) subid);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(owner);
		meta.setDisplayName(name);
		skull.setItemMeta(meta);
		return skull;
	}
}
