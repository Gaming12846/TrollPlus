/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.gaming12846.troll.utilitys.Vars;

public class Control implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (Vars.controller.containsKey(p)) {
			Vars.controller.get(p).teleport(p);
		}

	}

	public static HashMap<Player, String> message = new HashMap<>();

	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (Vars.controller.containsKey(p)) {
			// Player target = Var.controler.get(p);
			e.setCancelled(true);
			message.put(Vars.controller.get(p), e.getMessage());
			Vars.controller.get(p).chat(e.getMessage());
		}

	}

}
