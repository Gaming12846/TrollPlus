/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class HandItemDrop {

	public static void HandItemDrop() {

		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.handItemDropList.contains(Vars.target.getName())) {

					if (Vars.target.getInventory().getItemInMainHand().getAmount() > 0) {
						ItemStack item = Vars.target.getItemInHand();
						Item itemDrop = Vars.target.getWorld().dropItemNaturally(Vars.target.getLocation(),
								new ItemStack(Vars.target.getItemInHand().getType(), 1));
						itemDrop.setPickupDelay(20);
						int amount = item.getAmount();
						amount--;
						item.setAmount(amount);
						Vars.target.getInventory().setItemInHand(item);
					} else
						return;
				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 10, 10);
	}
}
