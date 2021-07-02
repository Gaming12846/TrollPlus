/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.ItemBuilder;
import de.gaming12846.trollplus.utilitys.Vars;
import net.md_5.bungee.api.ChatColor;

public class Control implements Listener {

	private static String message = null;
	private static boolean messageBoolean = false;

	private static Location location = null;

	private static ItemStack[] inventory = null;
	private static ItemStack[] armor = null;
	private static ItemStack offHandItem = null;

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.controlList.containsKey(p))
			e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (Vars.Lists.controlList.containsKey(p))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.controlList.containsKey(p))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.controlList.containsKey(p))
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();

		if (Vars.Lists.controlList.containsKey(damager))
			e.setCancelled(true);
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		// Controller
		if (Vars.Lists.controlList.containsValue(p)) {
			e.setCancelled(true);
			message = e.getMessage();
			messageBoolean = true;
		}

		// Victim
		if (Vars.Lists.controlList.containsKey(p)) {
			if (messageBoolean == false)
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (Vars.Lists.controlList.containsKey(p)) {
			Vars.Lists.controlList.remove(p);
			Vars.Status.controlStatus = "§c§lOFF";
			Vars.trollmenu.setItem(14, ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));

			p.getInventory().setContents(Vars.executer.getInventory().getContents());
			p.getInventory().setArmorContents(Vars.executer.getInventory().getArmorContents());
			p.getInventory().setItemInOffHand(Vars.executer.getInventory().getItemInOffHand());

			Vars.executer.getInventory().setContents(inventory);
			Vars.executer.getInventory().setArmorContents(armor);
			Vars.executer.getInventory().setItemInOffHand(offHandItem);
			Vars.executer.teleport(location);

			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.showPlayer(Vars.executer);
			}
			Vars.executer.showPlayer(Vars.target);
		}
	}

	public static void newControl(Player victim, Player controller) {
		for (Player online : Bukkit.getServer().getOnlinePlayers()) {
			online.hidePlayer(controller);
		}
		controller.hidePlayer(victim);

		location = Vars.executer.getLocation();
		inventory = Vars.executer.getInventory().getContents();
		armor = Vars.executer.getInventory().getArmorContents();
		offHandItem = Vars.executer.getInventory().getItemInOffHand();

		controller.teleport(victim);
		controller.getInventory().setContents(victim.getInventory().getContents());
		controller.getInventory().setArmorContents(victim.getInventory().getArmorContents());
		controller.getInventory().setItemInOffHand(victim.getInventory().getItemInOffHand());

		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.controlList.containsKey(victim)) {

					if (messageBoolean == true) {

						victim.chat(message);
						messageBoolean = false;
					}

					if (victim.getLocation() != controller.getLocation()) {

						victim.teleport(controller);
					}
				} else {

					victim.getInventory().setContents(controller.getInventory().getContents());
					victim.getInventory().setArmorContents(controller.getInventory().getArmorContents());
					victim.getInventory().setItemInOffHand(controller.getInventory().getItemInOffHand());

					controller.getInventory().setContents(inventory);
					controller.getInventory().setArmorContents(armor);
					controller.getInventory().setItemInOffHand(offHandItem);
					controller.teleport(location);

					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						online.showPlayer(controller);
					}
					controller.showPlayer(victim);

					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 1, 1);
	}
}
