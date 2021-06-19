/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.gaming12846.troll.utilitys.Vars;

public class FlipBehind implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.flipbehindList.contains(p.getName())) {
			double x = Vars.target.getLocation().getX();
			double y = Vars.target.getLocation().getY();
			double z = Vars.target.getLocation().getZ();
			double yaw = Vars.target.getEyeLocation().getYaw();
			World world = Vars.target.getLocation().getWorld();
			double yaw2 = yaw + 180;
			Location location = new Location(world, x, y, z);
			location.setYaw((float) yaw2);
			Vars.target.teleport(location);
		}
	}
}
