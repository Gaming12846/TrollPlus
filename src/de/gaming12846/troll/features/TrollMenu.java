/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.gaming12846.troll.utilitys.Items;
import de.gaming12846.troll.utilitys.Vars;

public class TrollMenu implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		try {

			if (e.getView().getTitle().equalsIgnoreCase("Troll §4" + Vars.target.getName())) {
				e.setCancelled(true);

				// Basic things
				if (e.getSlot() == 26) {
					p.closeInventory();
				} else if (e.getSlot() == 24) {
					p.openInventory(Vars.target.getPlayer().getInventory());
				} else if (e.getSlot() == 22) {
					Vars.target.setHealth(0.0);
				} else if (e.getSlot() == 20) {
					p.teleport(Vars.target);
				} else if (e.getSlot() == 18) {
					if (Vars.vanishlist.contains(p.getName()) && Vars.vanishstatus == "§bAll") {
						Vars.vanishlist.remove(Vars.target.getName());
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.showPlayer(p);
						}
						Vars.vanishstatus = "§cOFF";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishstatus));
					} else if (Vars.vanishlist.contains(p.getName())) {
						Vars.target.showPlayer(p);
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.hidePlayer(p);
						}
						Vars.vanishstatus = "§bAll";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishstatus));
					} else {
						Vars.vanishlist.add(Vars.target.getName());
						Vars.target.hidePlayer(p);
						Vars.vanishstatus = "§aTarget";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishstatus));
					}

					// Features
				} else if (e.getSlot() == 0) {
					if (Vars.freezelist.contains(Vars.target.getName())) {
						Vars.freezelist.remove(Vars.target.getName());
						Vars.target.removePotionEffect(PotionEffectType.SLOW);
						Vars.freezestatus = "§cOFF";
						Vars.trollmenu.setItem(0, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezestatus));
					} else {
						Vars.freezelist.add(Vars.target.getName());
						Vars.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
						Vars.freezestatus = "§aON";
						Vars.trollmenu.setItem(0, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezestatus));
					}
				} else if (e.getSlot() == 2) {
					if (Vars.handitemdroplist.contains(Vars.target.getName())) {
						Vars.handitemdroplist.remove(Vars.target.getName());
						Vars.handitemdropstatus = "§cOFF";
						Vars.trollmenu.setItem(2,
								Items.createItem(Material.FEATHER, 0, "§fHand item drop " + Vars.handitemdropstatus));
					} else {
						Vars.handitemdroplist.add(Vars.target.getName());
						Vars.handitemdropstatus = "§aON";
						Vars.trollmenu.setItem(2,
								Items.createItem(Material.FEATHER, 0, "§fHand item drop " + Vars.handitemdropstatus));
						HandItemDrop.HandItemDrop();
					}
				} else if (e.getSlot() == 4) {
					if (Vars.controller.containsKey(p)) {
						// if (Vars.controllist.contains(Vars.target.getName())) {
						// Vars.controllist.remove(Vars.target.getName());
						Vars.controller.remove(p, Vars.target);
						Vars.controlstatus = "§cOFF";
						Vars.trollmenu.setItem(4,
								Items.createItem(Material.LEAD, 0, "§fControl " + Vars.controlstatus));
						Vars.target.showPlayer(p);
						p.showPlayer(Vars.target);
					} else {
						// Vars.controllist.add(Vars.target.getName());
						Vars.controller.put(p, Vars.target);
						Vars.controlstatus = "§aON";
						Vars.trollmenu.setItem(4,
								Items.createItem(Material.LEAD, 0, "§fControl " + Vars.controlstatus));
						Vars.target.hidePlayer(p);
						p.hidePlayer(Vars.target);
					}
				} else if (e.getSlot() == 6) {
					if (Vars.flipbehindlist.contains(Vars.target.getName())) {
						Vars.flipbehindlist.remove(Vars.target.getName());
						Vars.flipbehindstatus = "§cOFF";
						Vars.trollmenu.setItem(6,
								Items.createItem(Material.EGG, 0, "§fFlip behind " + Vars.flipbehindstatus));
					} else {
						Vars.flipbehindlist.add(Vars.target.getName());
						Vars.flipbehindstatus = "§aON";
						Vars.trollmenu.setItem(6,
								Items.createItem(Material.EGG, 0, "§fFlip behind " + Vars.flipbehindstatus));
					}
				} else if (e.getSlot() == 8) {
					if (Vars.spamsoundslist.contains(Vars.target.getName())) {
						Vars.spamsoundslist.remove(Vars.target.getName());
						Vars.spamsoundsstatus = "§cOFF";
						Vars.trollmenu.setItem(8, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
								"§fSpam sounds " + Vars.spamsoundsstatus));
					} else {
						Vars.spamsoundslist.add(Vars.target.getName());
						Vars.spamsoundsstatus = "§aON";
						Vars.trollmenu.setItem(8, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
								"§fSpam sounds " + Vars.spamsoundsstatus));
						SpamSounds.SpamSounds();
					}
				} else if (e.getSlot() == 9) {
					if (Vars.spammessageslist.contains(Vars.target.getName())) {
						Vars.spammessageslist.remove(Vars.target.getName());
						Vars.spammessagesstatus = "§cOFF";
						Vars.trollmenu.setItem(9, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								"§fSpam messages " + Vars.spammessagesstatus));
					} else {
						Vars.spammessageslist.add(Vars.target.getName());
						Vars.spammessagesstatus = "§aON";
						Vars.trollmenu.setItem(9, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								"§fSpam messages " + Vars.spammessagesstatus));
						SpamMessages.SpamMessages();
					}
				} else if (e.getSlot() == 11) {
					List<Sound> sounds = new ArrayList<>();
					sounds.add(Sound.AMBIENT_BASALT_DELTAS_MOOD);
					sounds.add(Sound.AMBIENT_CAVE);
					sounds.add(Sound.AMBIENT_CRIMSON_FOREST_MOOD);
					sounds.add(Sound.AMBIENT_NETHER_WASTES_MOOD);
					sounds.add(Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD);
					sounds.add(Sound.AMBIENT_WARPED_FOREST_MOOD);

					Random random = new Random();
					Sound sound = sounds.get(random.nextInt(sounds.size()));
					Vars.target.playSound(Vars.target.getLocation(), sound, 200, 1);
				} else if (e.getSlot() == 17) {
					Vars.target.kickPlayer(Vars.banmessageplayer);

					if (Vars.bankmessagebroadcastonoff == true) {
						String banbroadcast = "";
						banbroadcast = Vars.banmessagebroadcast.replace("[Player]", Vars.target.getName());
						Bukkit.broadcastMessage(banbroadcast);
					}
				}

			}

		} catch (Exception e1) {
		}

	}

}
