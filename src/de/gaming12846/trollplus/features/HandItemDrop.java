/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class HandItemDrop {

	public static void handItemDrop(Player victim) {
		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.handItemDropList.contains(victim.getName())) {

					if (victim.getInventory().getItemInMainHand().getAmount() > 0) {
						ItemStack item = victim.getInventory().getItemInHand();
						ItemStack dropItem = new ItemStack(victim.getInventory().getItemInHand().getType(), 1);
						dropItem.setAmount(1);
						dropItem.setItemMeta(victim.getInventory().getItemInHand().getItemMeta());
						Item itemDrop = victim.getWorld().dropItemNaturally(victim.getLocation(), dropItem);
						itemDrop.setPickupDelay(20);
						int amount = item.getAmount();
						amount--;
						item.setAmount(amount);
						victim.getInventory().setItemInHand(item);
					} else
						return;
				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 10, 10);
	}
}
