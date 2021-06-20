/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.gaming12846.troll.main.Main;
import de.gaming12846.troll.utilitys.Vars;

public class TNTTrack implements Listener {

	public static void TNTTrack() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				if (Vars.tntTrackList.contains(Vars.target.getName())) {
					Entity tnt = Vars.target.getWorld().spawn(Vars.target.getLocation(), TNTPrimed.class);
					((TNTPrimed) tnt).setFuseTicks(100);
					((TNTPrimed) tnt).setCustomName("TNTTrack");
					((TNTPrimed) tnt).getWorld().playSound(Vars.target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
				}
			}
		}, 30, 30);
	}

	@EventHandler
	public void onExplosion(EntityExplodeEvent e) {

		if (e.getEntity().getType() == EntityType.PRIMED_TNT && e.getEntity().getName().equals("TNTTrack")) {
			e.setCancelled(true);
			e.getEntity().getLocation().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE,
					20, 1);
		}
	}
}
