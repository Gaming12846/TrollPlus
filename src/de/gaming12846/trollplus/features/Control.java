/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.gaming12846.trollplus.utilitys.Vars;

public class Control implements Listener {

	private HashMap<Player, String> message = new HashMap<>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (Vars.HashMaps.controller.containsKey(p)) {
			Vars.HashMaps.controller.get(p).teleport(p);
		}
	}

	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (Vars.HashMaps.controller.containsKey(p)) {
			// Player target = Var.controler.get(p);
			e.setCancelled(true);
			message.put(Vars.HashMaps.controller.get(p), e.getMessage());
			Vars.HashMaps.controller.get(p).chat(e.getMessage());
		}
	}
}
