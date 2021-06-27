/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class TNTTrack implements Listener {

	public static void TNTTrack() {

		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.tntTrackList.contains(Vars.target.getName())) {

					if (Vars.Lists.tntTrackList.contains(Vars.target.getName())) {
						Entity tnt = Vars.target.getWorld().spawn(Vars.target.getLocation(), TNTPrimed.class);
						((TNTPrimed) tnt).setFuseTicks(100);
						((TNTPrimed) tnt).setCustomName("TNTTrack");
						((TNTPrimed) tnt).getWorld().playSound(Vars.target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
					}
				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 15, 15);
	}

	@EventHandler
	public void onExplosion(EntityExplodeEvent e) {

		if (e.getEntity().getType() == EntityType.PRIMED_TNT && e.getEntity().getName().equals("TNTTrack")) {
			e.setCancelled(true);
			e.getEntity().getLocation().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
		}
	}
}
