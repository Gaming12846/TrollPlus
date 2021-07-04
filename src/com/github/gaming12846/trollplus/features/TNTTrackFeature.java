package com.github.gaming12846.trollplus.features;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features TNTTrackFeature.java
 *
 * @author Gaming12846
 */
public class TNTTrackFeature implements Listener {

	public static void tntTrack(Player target) {
		new BukkitRunnable() {
			@Override
			public void run() {

				if (Vars.Lists.tntTrackList.contains(target.getName())) {

					if (Vars.Lists.tntTrackList.contains(target.getName())) {
						Entity tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
						((TNTPrimed) tnt).setFuseTicks(100);
						((TNTPrimed) tnt).setCustomName("TNTTrack");
						((TNTPrimed) tnt).getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
					}
				} else
					cancel();
			}
		}.runTaskTimer(TrollPlus.getPlugin(), 0, 15);
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {

		if (e.getEntity().getType() == EntityType.PRIMED_TNT && e.getEntity().getName().equals("TNTTrack")) {
			e.setCancelled(true);
			e.getEntity().getLocation().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
		}
	}
}