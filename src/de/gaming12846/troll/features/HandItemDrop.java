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

	public static void AutoHandItemDrop() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				if (Vars.handitemdroplist.contains(Vars.target.getName())) {

					if (Vars.target.getInventory().getItemInMainHand().getAmount() > 0) {
						ItemStack item = Vars.target.getItemInHand();
						Vars.target.getInventory().remove(item);
						Item itemDropped = Vars.target.getWorld().dropItemNaturally(Vars.target.getLocation(), item);
						itemDropped.setPickupDelay(40);
					}

				}

			}

		}, 10, 10);

	}

}
