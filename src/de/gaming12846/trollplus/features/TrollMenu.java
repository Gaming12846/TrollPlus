/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

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

import de.gaming12846.trollplus.utilitys.Items;
import de.gaming12846.trollplus.utilitys.Vars;

public class TrollMenu implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		try {

			if (e.getView().getTitle().equalsIgnoreCase("Troll §l§c" + Vars.target.getName())) {
				e.setCancelled(true);

				// Basic things
				if (e.getSlot() == 53) {
					p.closeInventory();
				} else if (e.getSlot() == 51) {
					p.openInventory(Vars.target.getPlayer().getInventory());
				} else if (e.getSlot() == 50) {
					Vars.target.setHealth(0.0);
				} else if (e.getSlot() == 48) {
					p.teleport(Vars.target);
				} else if (e.getSlot() == 47) {
					if (Vars.Lists.vanishList.contains(p.getName()) && Vars.Status.vanishStatus == "§b§lAll") {
						Vars.Lists.vanishList.remove(Vars.target.getName());
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.showPlayer(p);
						}
						Vars.Status.vanishStatus = "§c§lOFF";
						Vars.trollmenu.setItem(47, Items.createItem(Material.POTION, 0, "§fVanish " + Vars.Status.vanishStatus));
					} else if (Vars.Lists.vanishList.contains(p.getName())) {
						Vars.target.showPlayer(p);
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.hidePlayer(p);
						}
						Vars.Status.vanishStatus = "§b§lAll";
						Vars.trollmenu.setItem(47, Items.createItem(Material.POTION, 0, "§fVanish " + Vars.Status.vanishStatus));
					} else {
						Vars.Lists.vanishList.add(Vars.target.getName());
						Vars.target.hidePlayer(p);
						Vars.Status.vanishStatus = "§a§lTarget";
						Vars.trollmenu.setItem(47, Items.createItem(Material.POTION, 0, "§fVanish " + Vars.Status.vanishStatus));
					}

					// Features
				} else if (e.getSlot() == 10) {
					if (Vars.Lists.freezeList.contains(Vars.target.getName())) {
						Vars.Lists.freezeList.remove(Vars.target.getName());
						Vars.target.removePotionEffect(PotionEffectType.SLOW);
						Vars.Status.freezeStatus = "§c§lOFF";
						Vars.trollmenu.setItem(10, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.Status.freezeStatus));
					} else {
						Vars.Lists.freezeList.add(Vars.target.getName());
						Vars.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
						Vars.Status.freezeStatus = "§a§lON";
						Vars.trollmenu.setItem(10, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.Status.freezeStatus));
					}
				} else if (e.getSlot() == 12) {
					if (Vars.Lists.handitemdropList.contains(Vars.target.getName())) {
						Vars.Lists.handitemdropList.remove(Vars.target.getName());
						Vars.Status.handitemdropStatus = "§c§lOFF";
						Vars.trollmenu.setItem(12,
								Items.createItem(Material.SHEARS, 0, "§fHand item drop " + Vars.Status.handitemdropStatus));
					} else {
						Vars.Lists.handitemdropList.add(Vars.target.getName());
						Vars.Status.handitemdropStatus = "§a§lON";
						Vars.trollmenu.setItem(12,
								Items.createItem(Material.SHEARS, 0, "§fHand item drop " + Vars.Status.handitemdropStatus));
						HandItemDrop.HandItemDrop();
					}
				} else if (e.getSlot() == 14) {
					if (Vars.HashMaps.controller.containsKey(p)) {
						// if (Vars.controlList.contains(Vars.target.getName())) {
						// Vars.controlList.remove(Vars.target.getName());
						Vars.HashMaps.controller.remove(p, Vars.target);
						Vars.Status.controlStatus = "§c§lOFF";
						Vars.trollmenu.setItem(14, Items.createItem(Material.LEAD, 0, "§fControl " + Vars.Status.controlStatus));
						Vars.target.showPlayer(p);
						p.showPlayer(Vars.target);
					} else {
						// Vars.controlList.add(Vars.target.getName());
						Vars.HashMaps.controller.put(p, Vars.target);
						Vars.Status.controlStatus = "§a§lON";
						Vars.trollmenu.setItem(14, Items.createItem(Material.LEAD, 0, "§fControl " + Vars.Status.controlStatus));
						Vars.target.hidePlayer(p);
						p.hidePlayer(Vars.target);
					}
				} else if (e.getSlot() == 16) {
					if (Vars.Lists.flipbehindList.contains(Vars.target.getName())) {
						Vars.Lists.flipbehindList.remove(Vars.target.getName());
						Vars.Status.flipbehindStatus = "§c§lOFF";
						Vars.trollmenu.setItem(16, Items.createItem(Material.COMPASS, 0, "§fFlip behind " + Vars.Status.flipbehindStatus));
					} else {
						Vars.Lists.flipbehindList.add(Vars.target.getName());
						Vars.Status.flipbehindStatus = "§a§lON";
						Vars.trollmenu.setItem(16, Items.createItem(Material.COMPASS, 0, "§fFlip behind " + Vars.Status.flipbehindStatus));
					}
				} else if (e.getSlot() == 20) {
					if (Vars.Lists.spammessagesList.contains(Vars.target.getName())) {
						Vars.Lists.spammessagesList.remove(Vars.target.getName());
						Vars.Status.spammessagesStatus = "§c§lOFF";
						Vars.trollmenu.setItem(20,
								Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0, "§fSpam messages " + Vars.Status.spammessagesStatus));
					} else {
						Vars.Lists.spammessagesList.add(Vars.target.getName());
						Vars.Status.spammessagesStatus = "§a§lON";
						Vars.trollmenu.setItem(20,
								Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0, "§fSpam messages " + Vars.Status.spammessagesStatus));
						SpamMessages.SpamMessages();
					}
				} else if (e.getSlot() == 22) {
					if (Vars.Lists.spamsoundsList.contains(Vars.target.getName())) {
						Vars.Lists.spamsoundsList.remove(Vars.target.getName());
						Vars.Status.spamsoundsStatus = "§c§lOFF";
						Vars.trollmenu.setItem(22,
								Items.createItem(Material.NOTE_BLOCK, 0, "§fSpam sounds " + Vars.Status.spamsoundsStatus));
					} else {
						Vars.Lists.spamsoundsList.add(Vars.target.getName());
						Vars.Status.spamsoundsStatus = "§a§lON";
						Vars.trollmenu.setItem(22,
								Items.createItem(Material.NOTE_BLOCK, 0, "§fSpam sounds " + Vars.Status.spamsoundsStatus));
						SpamSounds.SpamSounds();
					}

				} else if (e.getSlot() == 24) {
					if (Vars.Lists.semibanList.contains(Vars.target.getName())) {
						Vars.Lists.semibanList.remove(Vars.target.getName());
						Vars.Status.semibanStatus = "§c§lOFF";
						Vars.trollmenu.setItem(24, Items.createItem(Material.TRIPWIRE_HOOK, 0, "§fSemi ban " + Vars.Status.semibanStatus));
					} else {
						Vars.Lists.semibanList.add(Vars.target.getName());
						Vars.Status.semibanStatus = "§a§lON";
						Vars.trollmenu.setItem(24, Items.createItem(Material.TRIPWIRE_HOOK, 0, "§fSemi ban " + Vars.Status.semibanStatus));
					}
				} else if (e.getSlot() == 28) {
					if (Vars.Lists.tnttrackList.contains(Vars.target.getName())) {
						Vars.Lists.tnttrackList.remove(Vars.target.getName());
						Vars.Status.tnttrackStatus = "§c§lOFF";
						Vars.trollmenu.setItem(28, Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.Status.tnttrackStatus));
					} else {
						Vars.Lists.tnttrackList.add(Vars.target.getName());
						Vars.Status.tnttrackStatus = "§a§lON";
						Vars.trollmenu.setItem(28, Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.Status.tnttrackStatus));
						TNTTrack.TNTTrack();
					}
				} else if (e.getSlot() == 30) {
					Vars.target.kickPlayer(Vars.Messages.banMessagePlayer);

					if (Vars.Booleans.banMessageBroadcastOnOff == true) {
						String banbroadcast = "";
						banbroadcast = Vars.Messages.banMessageBroadcast.replace("[Player]", Vars.target.getName());
						Bukkit.broadcastMessage(banbroadcast);
					}
				} else if (e.getSlot() == 32) {
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
				}
			}
		} catch (Exception e1) {
		}
	}
}
