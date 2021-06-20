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
					if (Vars.vanishList.contains(p.getName()) && Vars.vanishStatus == "§bAll") {
						Vars.vanishList.remove(Vars.target.getName());
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.showPlayer(p);
						}
						Vars.vanishStatus = "§cOFF";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishStatus));
					} else if (Vars.vanishList.contains(p.getName())) {
						Vars.target.showPlayer(p);
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.hidePlayer(p);
						}
						Vars.vanishStatus = "§bAll";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishStatus));
					} else {
						Vars.vanishList.add(Vars.target.getName());
						Vars.target.hidePlayer(p);
						Vars.vanishStatus = "§aTarget";
						Vars.trollmenu.setItem(18,
								Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishStatus));
					}

					// Features
				} else if (e.getSlot() == 0) {
					if (Vars.freezeList.contains(Vars.target.getName())) {
						Vars.freezeList.remove(Vars.target.getName());
						Vars.target.removePotionEffect(PotionEffectType.SLOW);
						Vars.freezeStatus = "§cOFF";
						Vars.trollmenu.setItem(0, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezeStatus));
					} else {
						Vars.freezeList.add(Vars.target.getName());
						Vars.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
						Vars.freezeStatus = "§aON";
						Vars.trollmenu.setItem(0, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezeStatus));
					}
				} else if (e.getSlot() == 2) {
					if (Vars.handitemdropList.contains(Vars.target.getName())) {
						Vars.handitemdropList.remove(Vars.target.getName());
						Vars.handitemdropStatus = "§cOFF";
						Vars.trollmenu.setItem(2,
								Items.createItem(Material.FEATHER, 0, "§fHand item drop " + Vars.handitemdropStatus));
					} else {
						Vars.handitemdropList.add(Vars.target.getName());
						Vars.handitemdropStatus = "§aON";
						Vars.trollmenu.setItem(2,
								Items.createItem(Material.FEATHER, 0, "§fHand item drop " + Vars.handitemdropStatus));
						HandItemDrop.HandItemDrop();
					}
				} else if (e.getSlot() == 4) {
					if (Vars.controller.containsKey(p)) {
						// if (Vars.controlList.contains(Vars.target.getName())) {
						// Vars.controlList.remove(Vars.target.getName());
						Vars.controller.remove(p, Vars.target);
						Vars.controlStatus = "§cOFF";
						Vars.trollmenu.setItem(4,
								Items.createItem(Material.LEAD, 0, "§fControl " + Vars.controlStatus));
						Vars.target.showPlayer(p);
						p.showPlayer(Vars.target);
					} else {
						// Vars.controlList.add(Vars.target.getName());
						Vars.controller.put(p, Vars.target);
						Vars.controlStatus = "§aON";
						Vars.trollmenu.setItem(4,
								Items.createItem(Material.LEAD, 0, "§fControl " + Vars.controlStatus));
						Vars.target.hidePlayer(p);
						p.hidePlayer(Vars.target);
					}
				} else if (e.getSlot() == 6) {
					if (Vars.flipbehindList.contains(Vars.target.getName())) {
						Vars.flipbehindList.remove(Vars.target.getName());
						Vars.flipbehindStatus = "§cOFF";
						Vars.trollmenu.setItem(6,
								Items.createItem(Material.EGG, 0, "§fFlip behind " + Vars.flipbehindStatus));
					} else {
						Vars.flipbehindList.add(Vars.target.getName());
						Vars.flipbehindStatus = "§aON";
						Vars.trollmenu.setItem(6,
								Items.createItem(Material.EGG, 0, "§fFlip behind " + Vars.flipbehindStatus));
					}
				} else if (e.getSlot() == 8) {
					if (Vars.spamsoundsList.contains(Vars.target.getName())) {
						Vars.spamsoundsList.remove(Vars.target.getName());
						Vars.spamsoundsStatus = "§cOFF";
						Vars.trollmenu.setItem(8, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
								"§fSpam sounds " + Vars.spamsoundsStatus));
					} else {
						Vars.spamsoundsList.add(Vars.target.getName());
						Vars.spamsoundsStatus = "§aON";
						Vars.trollmenu.setItem(8, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
								"§fSpam sounds " + Vars.spamsoundsStatus));
						SpamSounds.SpamSounds();
					}
				} else if (e.getSlot() == 9) {
					if (Vars.spammessagesList.contains(Vars.target.getName())) {
						Vars.spammessagesList.remove(Vars.target.getName());
						Vars.spammessagesStatus = "§cOFF";
						Vars.trollmenu.setItem(9, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								"§fSpam messages " + Vars.spammessagesStatus));
					} else {
						Vars.spammessagesList.add(Vars.target.getName());
						Vars.spammessagesStatus = "§aON";
						Vars.trollmenu.setItem(9, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								"§fSpam messages " + Vars.spammessagesStatus));
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
					Vars.target.kickPlayer(Vars.banMessagePlayer);

					if (Vars.bankMessageBroadcastOnOff == true) {
						String banbroadcast = "";
						banbroadcast = Vars.banMessageBroadcast.replace("[Player]", Vars.target.getName());
						Bukkit.broadcastMessage(banbroadcast);
					}
				} else if (e.getSlot() == 15) {
					if (Vars.semiBanList.contains(Vars.target.getName())) {
						Vars.semiBanList.remove(Vars.target.getName());
						Vars.semiBanStatus = "§cOFF";
						Vars.trollmenu.setItem(15,
								Items.createItem(Material.SHEARS, 0, "§fSemi ban " + Vars.semiBanStatus));
					} else {
						Vars.semiBanList.add(Vars.target.getName());
						Vars.semiBanStatus = "§aON";
						Vars.trollmenu.setItem(15,
								Items.createItem(Material.SHEARS, 0, "§fSemi ban " + Vars.semiBanStatus));
					}
				} else if (e.getSlot() == 13) {
					if (Vars.tntTrackList.contains(Vars.target.getName())) {
						Vars.tntTrackList.remove(Vars.target.getName());
						Vars.tntTrackStatus = "§cOFF";
						Vars.trollmenu.setItem(13,
								Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.tntTrackStatus));
					} else {
						Vars.tntTrackList.add(Vars.target.getName());
						Vars.tntTrackStatus = "§aON";
						Vars.trollmenu.setItem(13,
								Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.tntTrackStatus));
						TNTTrack.TNTTrack();
					}
				}
			}
		} catch (Exception e1) {
		}
	}
}
