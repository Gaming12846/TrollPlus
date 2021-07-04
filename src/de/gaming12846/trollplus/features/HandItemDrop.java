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

	public static void handItemDrop(Player target) {
		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.handItemDropList.contains(target.getName())) {

					if (target.getInventory().getItemInMainHand().getAmount() > 0) {
						ItemStack item = target.getInventory().getItemInHand();
						ItemStack dropItem = new ItemStack(target.getInventory().getItemInHand().getType(), 1);
						dropItem.setAmount(1);
						dropItem.setItemMeta(target.getInventory().getItemInHand().getItemMeta());
						Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
						itemDrop.setPickupDelay(20);
						int amount = item.getAmount();
						amount--;
						item.setAmount(amount);
						target.getInventory().setItemInHand(item);
					} else
						return;
				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 0, 10);
	}
}
