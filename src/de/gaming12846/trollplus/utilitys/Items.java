/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.utilitys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	// Easy methode to create a itemstack
	public static ItemStack createItem(Material mat, int subid, String name) {
		ItemStack item = new ItemStack(mat, 1, (short) subid);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
