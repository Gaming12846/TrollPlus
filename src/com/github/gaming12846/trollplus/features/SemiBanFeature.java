package com.github.gaming12846.trollplus.features;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features SemiBanFeature.java
 *
 * @author Gaming12846
 */
public class SemiBanFeature implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.semiBanList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (Vars.Lists.semiBanList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.semiBanList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.semiBanList.contains(p.getName()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();

		if (Vars.Lists.semiBanList.contains(damager.getName()))
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
}