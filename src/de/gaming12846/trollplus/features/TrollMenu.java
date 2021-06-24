/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.gaming12846.trollplus.utilitys.ItemBuilder;
import de.gaming12846.trollplus.utilitys.Vars;
import net.md_5.bungee.api.ChatColor;

public class TrollMenu implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		try {

			if (e.getView().getTitle().equalsIgnoreCase("Troll §c§l" + Vars.target.getName())) {
				e.setCancelled(true);

				switch (e.getSlot()) {
				default:
					return;
				// Basic things
				case 53:
					p.closeInventory();
					break;
				case 51:
					p.openInventory(Vars.target.getPlayer().getInventory());
					break;
				case 50:
					Vars.target.setHealth(0.0);
					break;
				case 48:
					p.teleport(Vars.target);
					break;
				case 47:
					if (Vars.Lists.vanishList.contains(p.getName()) && Vars.Status.vanishStatus == "§b§lAll") {
						Vars.Lists.vanishList.remove(Vars.target.getName());
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.showPlayer(p);
						}
						Vars.Status.vanishStatus = "§c§lOFF";
						Vars.trollmenu.setItem(47,
								ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
					} else if (Vars.Lists.vanishList.contains(p.getName())) {
						Vars.target.showPlayer(p);
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.hidePlayer(p);
						}
						Vars.Status.vanishStatus = "§b§lAll";
						Vars.trollmenu.setItem(47,
								ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
					} else {
						Vars.Lists.vanishList.add(Vars.target.getName());
						Vars.target.hidePlayer(p);
						Vars.Status.vanishStatus = "§a§lTarget";
						Vars.trollmenu.setItem(47,
								ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
					}
					break;
				// Features
				case 10:
					if (Vars.Lists.freezeList.contains(Vars.target.getName())) {
						Vars.Lists.freezeList.remove(Vars.target.getName());
						Vars.target.removePotionEffect(PotionEffectType.SLOW);
						Vars.Status.freezeStatus = "§c§lOFF";
						Vars.trollmenu.setItem(10, ItemBuilder.createItem(Material.ICE, 0, ChatColor.WHITE + "Freeze " + Vars.Status.freezeStatus));
					} else {
						Vars.Lists.freezeList.add(Vars.target.getName());
						Vars.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
						Vars.Status.freezeStatus = "§a§lON";
						Vars.trollmenu.setItem(10, ItemBuilder.createItem(Material.ICE, 0, ChatColor.WHITE + "Freeze " + Vars.Status.freezeStatus));
					}
					break;
				case 12:
					if (Vars.Lists.handItemDropList.contains(Vars.target.getName())) {
						Vars.Lists.handItemDropList.remove(Vars.target.getName());
						Vars.Status.handItemDropStatus = "§c§lOFF";
						Vars.trollmenu.setItem(12,
								ItemBuilder.createItem(Material.SHEARS, 0, ChatColor.WHITE + "Hand item drop " + Vars.Status.handItemDropStatus));
					} else {
						Vars.Lists.handItemDropList.add(Vars.target.getName());
						Vars.Status.handItemDropStatus = "§a§lON";
						Vars.trollmenu.setItem(12,
								ItemBuilder.createItem(Material.SHEARS, 0, ChatColor.WHITE + "Hand item drop " + Vars.Status.handItemDropStatus));
						HandItemDrop.HandItemDrop();
					}
					break;
				case 14:
					if (Vars.HashMaps.controller.containsKey(p)) {
						// if (Vars.controlList.contains(Vars.target.getName())) {
						// Vars.controlList.remove(Vars.target.getName());
						Vars.HashMaps.controller.remove(p, Vars.target);
						Vars.Status.controlStatus = "§c§lOFF";
						Vars.trollmenu.setItem(14,
								ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));
						Vars.target.showPlayer(p);
						p.showPlayer(Vars.target);
					} else {
						// Vars.controlList.add(Vars.target.getName());
						Vars.HashMaps.controller.put(p, Vars.target);
						Vars.Status.controlStatus = "§a§lON";
						Vars.trollmenu.setItem(14,
								ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));
						Vars.target.hidePlayer(p);
						p.hidePlayer(Vars.target);
					}
					break;
				case 16:
					if (Vars.Lists.flipBehindList.contains(Vars.target.getName())) {
						Vars.Lists.flipBehindList.remove(Vars.target.getName());
						Vars.Status.flipBehindStatus = "§c§lOFF";
						Vars.trollmenu.setItem(16,
								ItemBuilder.createItem(Material.COMPASS, 0, ChatColor.WHITE + "Flip behind " + Vars.Status.flipBehindStatus));
					} else {
						Vars.Lists.flipBehindList.add(Vars.target.getName());
						Vars.Status.flipBehindStatus = "§a§lON";
						Vars.trollmenu.setItem(16,
								ItemBuilder.createItem(Material.COMPASS, 0, ChatColor.WHITE + "Flip behind " + Vars.Status.flipBehindStatus));
					}
					break;
				case 20:
					if (Vars.Lists.spamMessagesList.contains(Vars.target.getName())) {
						Vars.Lists.spamMessagesList.remove(Vars.target.getName());
						Vars.Status.spamMessagesStatus = "§c§lOFF";
						Vars.trollmenu.setItem(20, ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								ChatColor.WHITE + "Spam messages " + Vars.Status.spamMessagesStatus));
					} else {
						Vars.Lists.spamMessagesList.add(Vars.target.getName());
						Vars.Status.spamMessagesStatus = "§a§lON";
						Vars.trollmenu.setItem(20, ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
								ChatColor.WHITE + "Spam messages " + Vars.Status.spamMessagesStatus));
						SpamMessages.SpamMessages();
					}
					break;
				case 22:
					if (Vars.Lists.spamSoundsList.contains(Vars.target.getName())) {
						Vars.Lists.spamSoundsList.remove(Vars.target.getName());
						Vars.Status.spamSoundsStatus = "§c§lOFF";
						Vars.trollmenu.setItem(22,
								ItemBuilder.createItem(Material.NOTE_BLOCK, 0, ChatColor.WHITE + "Spam sounds " + Vars.Status.spamSoundsStatus));
					} else {
						Vars.Lists.spamSoundsList.add(Vars.target.getName());
						Vars.Status.spamSoundsStatus = "§a§lON";
						Vars.trollmenu.setItem(22,
								ItemBuilder.createItem(Material.NOTE_BLOCK, 0, ChatColor.WHITE + "Spam sounds " + Vars.Status.spamSoundsStatus));
						SpamSounds.SpamSounds();
					}
					break;
				case 24:
					if (Vars.Lists.semiBanList.contains(Vars.target.getName())) {
						Vars.Lists.semiBanList.remove(Vars.target.getName());
						Vars.Status.semiBanStatus = "§c§lOFF";
						Vars.trollmenu.setItem(24,
								ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 0, ChatColor.WHITE + "Semi ban " + Vars.Status.semiBanStatus));
					} else {
						Vars.Lists.semiBanList.add(Vars.target.getName());
						Vars.Status.semiBanStatus = "§a§lON";
						Vars.trollmenu.setItem(24,
								ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 0, ChatColor.WHITE + "Semi ban " + Vars.Status.semiBanStatus));
					}
					break;
				case 28:
					if (Vars.Lists.tntTrackList.contains(Vars.target.getName())) {
						Vars.Lists.tntTrackList.remove(Vars.target.getName());
						Vars.Status.tntTrackStatus = "§c§lOFF";
						Vars.trollmenu.setItem(28,
								ItemBuilder.createItem(Material.TNT, 0, ChatColor.WHITE + "TNT track " + Vars.Status.tntTrackStatus));
					} else {
						Vars.Lists.tntTrackList.add(Vars.target.getName());
						Vars.Status.tntTrackStatus = "§a§lON";
						Vars.trollmenu.setItem(28,
								ItemBuilder.createItem(Material.TNT, 0, ChatColor.WHITE + "TNT track " + Vars.Status.tntTrackStatus));
						TNTTrack.TNTTrack();
					}
					break;
				case 30:
					Vars.target.kickPlayer(Vars.Messages.banMessagePlayer);
					if (Vars.Booleans.banMessageBroadcastSwitch == true) {
						String banbroadcast = Vars.Messages.banMessageBroadcast.replace("[Player]", Vars.target.getName());
						Bukkit.broadcastMessage(banbroadcast);
					}
					break;
				case 32:
					List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD,
							Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD,
							Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);
					Vars.target.playSound(Vars.target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), 200, 1);
					break;
				}
			}
		} catch (Exception e1) {
		}
	}
}
