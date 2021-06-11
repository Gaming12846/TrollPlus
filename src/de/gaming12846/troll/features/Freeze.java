/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.gaming12846.troll.utilitys.Vars;

public class Freeze implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (Vars.freezelist.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.freezelist.contains(p.getName()))
			e.setCancelled(true);
	}

}
