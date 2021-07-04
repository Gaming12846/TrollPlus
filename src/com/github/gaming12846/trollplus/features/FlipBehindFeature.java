package com.github.gaming12846.trollplus.features;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features FlipBehindFeature.java
 *
 * @author Gaming12846
 */
public class FlipBehindFeature implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.flipBehindList.contains(p.getName())) {
			Location location = new Location(Vars.target.getLocation().getWorld(), Vars.target.getLocation().getX(), Vars.target.getLocation().getY(),
					Vars.target.getLocation().getZ());
			location.setYaw(Vars.target.getEyeLocation().getYaw() + 180);
			Vars.target.teleport(location);
		}
	}
}