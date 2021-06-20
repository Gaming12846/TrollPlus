/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class HandItemDrop {

	public static void HandItemDrop() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				if (Vars.Lists.handitemdropList.contains(Vars.target.getName())) {

					if (Vars.target.getInventory().getItemInMainHand().getAmount() > 0) {
						ItemStack item = Vars.target.getItemInHand();
						ItemStack itemDrop = new ItemStack(Vars.target.getItemInHand().getType(), 1);
						Item itemDropped = Vars.target.getWorld().dropItemNaturally(Vars.target.getLocation(), itemDrop);
						itemDropped.setPickupDelay(20);
						int amount = item.getAmount();
						amount--;
						item.setAmount(amount);
						Vars.target.getInventory().setItemInHand(item);
					}
				}
			}
		}, 10, 10);
	}
}
