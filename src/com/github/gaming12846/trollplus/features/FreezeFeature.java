package com.github.gaming12846.trollplus.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features FreezeFeature.java
 *
 * @author Gaming12846
 */
public class FreezeFeature implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.freezeList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.freezeList.contains(p.getName()))
			e.setCancelled(true);
	}
}
