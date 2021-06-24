/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.gaming12846.trollplus.utilitys.Vars;

public class SemiBan implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.semiBanList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.semiBanList.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(Vars.Messages.semiBanMessage.replace("[Player]", p.getName()) + " " + e.getMessage());
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();

		if (Vars.Lists.semiBanList.contains(damager.getName()))
			e.setCancelled(true);
	}
}
