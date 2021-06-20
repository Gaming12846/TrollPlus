/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import de.gaming12846.troll.main.Main;
import de.gaming12846.troll.utilitys.Vars;

public class HandItemDrop {

	public static void HandItemDrop() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				if (Vars.Lists.handitemdropList.contains(Vars.target.getName())) {

					if (Vars.target.getInventory().getItemInMainHand().getAmount() != 0) {
						ItemStack item = Vars.target.getItemInHand();
						int amount = item.getAmount();
						amount--;
						item.setAmount(amount);
						Vars.target.getInventory().setItemInHand(item);
						ItemStack itemDrop = new ItemStack(Vars.target.getItemInHand().getType(), 1);
						Item itemDropped = Vars.target.getWorld().dropItemNaturally(Vars.target.getLocation(),
								itemDrop);
						itemDropped.setPickupDelay(20);
					}
				}
			}
		}, 10, 10);
	}
}
