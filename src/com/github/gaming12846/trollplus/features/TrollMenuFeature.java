package com.github.gaming12846.trollplus.features;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.Vars;

import net.md_5.bungee.api.ChatColor;

/**
 * TrollPlus com.github.gaming12846.trollplus.features TrollMenuFeature.java
 *
 * @author Gaming12846
 */
public class TrollMenuFeature implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

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
				if (Vars.Lists.vanishList.contains(Vars.target.getName()) && Vars.Status.vanishStatus == "§b§lAll") {
					Vars.Lists.vanishList.remove(Vars.target.getName());
					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						online.showPlayer(p);
					}
					Vars.Status.vanishStatus = "§c§lOFF";
					Vars.trollmenu.setItem(47, ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
				} else if (Vars.Lists.vanishList.contains(Vars.target.getName())) {
					Vars.target.showPlayer(p);
					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						online.hidePlayer(p);
					}
					Vars.Status.vanishStatus = "§b§lAll";
					Vars.trollmenu.setItem(47, ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
				} else {
					Vars.Lists.vanishList.add(Vars.target.getName());
					Vars.target.hidePlayer(p);
					Vars.Status.vanishStatus = "§a§lTarget";
					Vars.trollmenu.setItem(47, ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
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
					Vars.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 6));
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
					HandItemDropFeature.handItemDrop(Vars.target);
				}
				break;
			case 14:
				if (Vars.Lists.controlList.containsKey(Vars.target)) {
					Vars.Lists.controlList.remove(Vars.target);
					Vars.Status.controlStatus = "§c§lOFF";
					Vars.trollmenu.setItem(14, ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));
				} else if (Vars.target != p) {
					Vars.Lists.controlList.put(Vars.target, p);
					Vars.Status.controlStatus = "§a§lON";
					Vars.trollmenu.setItem(14, ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));
					ControlFeature.newControl(Vars.target, p);
				} else
					p.sendMessage(Vars.Messages.notAllowedControl);
				break;
			case 16:
				if (Vars.Lists.flipBehindList.contains(Vars.target.getName())) {
					Vars.Lists.flipBehindList.remove(Vars.target.getName());
					Vars.Status.flipBehindStatus = "§c§lOFF";
					Vars.trollmenu.setItem(16, ItemBuilder.createItem(Material.COMPASS, 0, ChatColor.WHITE + "Flip behind " + Vars.Status.flipBehindStatus));
				} else {
					Vars.Lists.flipBehindList.add(Vars.target.getName());
					Vars.Status.flipBehindStatus = "§a§lON";
					Vars.trollmenu.setItem(16, ItemBuilder.createItem(Material.COMPASS, 0, ChatColor.WHITE + "Flip behind " + Vars.Status.flipBehindStatus));
				}
				break;
			case 20:
				if (Vars.Lists.spamMessagesList.contains(Vars.target.getName())) {
					Vars.Lists.spamMessagesList.remove(Vars.target.getName());
					Vars.Status.spamMessagesStatus = "§c§lOFF";
					Vars.trollmenu.setItem(20,
							ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 0, ChatColor.WHITE + "Spam messages " + Vars.Status.spamMessagesStatus));
				} else {
					Vars.Lists.spamMessagesList.add(Vars.target.getName());
					Vars.Status.spamMessagesStatus = "§a§lON";
					Vars.trollmenu.setItem(20,
							ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 0, ChatColor.WHITE + "Spam messages " + Vars.Status.spamMessagesStatus));
					SpamMessagesFeature.spamMessages(Vars.target);
				}
				break;
			case 22:
				if (Vars.Lists.spamSoundsList.contains(Vars.target.getName())) {
					Vars.Lists.spamSoundsList.remove(Vars.target.getName());
					Vars.Status.spamSoundsStatus = "§c§lOFF";
					Vars.trollmenu.setItem(22, ItemBuilder.createItem(Material.NOTE_BLOCK, 0, ChatColor.WHITE + "Spam sounds " + Vars.Status.spamSoundsStatus));
				} else {
					Vars.Lists.spamSoundsList.add(Vars.target.getName());
					Vars.Status.spamSoundsStatus = "§a§lON";
					Vars.trollmenu.setItem(22, ItemBuilder.createItem(Material.NOTE_BLOCK, 0, ChatColor.WHITE + "Spam sounds " + Vars.Status.spamSoundsStatus));
					SoundsFeature.spamSounds(Vars.target);
				}
				break;
			case 24:
				if (Vars.Lists.semiBanList.contains(Vars.target.getName())) {
					Vars.Lists.semiBanList.remove(Vars.target.getName());
					Vars.Status.semiBanStatus = "§c§lOFF";
					Vars.trollmenu.setItem(24, ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 0, ChatColor.WHITE + "Semi ban " + Vars.Status.semiBanStatus));
				} else {
					Vars.Lists.semiBanList.add(Vars.target.getName());
					Vars.Status.semiBanStatus = "§a§lON";
					Vars.trollmenu.setItem(24, ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 0, ChatColor.WHITE + "Semi ban " + Vars.Status.semiBanStatus));
				}
				break;
			case 28:
				if (Vars.Lists.tntTrackList.contains(Vars.target.getName())) {
					Vars.Lists.tntTrackList.remove(Vars.target.getName());
					Vars.Status.tntTrackStatus = "§c§lOFF";
					Vars.trollmenu.setItem(28, ItemBuilder.createItem(Material.TNT, 0, ChatColor.WHITE + "TNT track " + Vars.Status.tntTrackStatus));
				} else {
					Vars.Lists.tntTrackList.add(Vars.target.getName());
					Vars.Status.tntTrackStatus = "§a§lON";
					Vars.trollmenu.setItem(28, ItemBuilder.createItem(Material.TNT, 0, ChatColor.WHITE + "TNT track " + Vars.Status.tntTrackStatus));
					TNTTrackFeature.tntTrack(Vars.target);
				}
				break;
			case 30:
				if (Vars.Lists.mobSpawnerList.contains(Vars.target.getName())) {
					Vars.Lists.mobSpawnerList.remove(Vars.target.getName());
					Vars.Status.mobSpawnerStatus = "§c§lOFF";
					Vars.trollmenu.setItem(30, ItemBuilder.createItem(Material.SPAWNER, 0, ChatColor.WHITE + "Mob spawner " + Vars.Status.mobSpawnerStatus));
				} else {
					Vars.Lists.mobSpawnerList.add(Vars.target.getName());
					Vars.Status.mobSpawnerStatus = "§a§lON";
					Vars.trollmenu.setItem(30, ItemBuilder.createItem(Material.SPAWNER, 0, ChatColor.WHITE + "Mob spawner " + Vars.Status.mobSpawnerStatus));
					MobSpawnerFeature.mobSpawner(Vars.target);
				}
				break;
			case 32:
				Vars.target.kickPlayer(Vars.Messages.banMessagePlayer);
				if (Vars.Booleans.banMessageBroadcastSwitch == true) {
					String banbroadcast = Vars.Messages.banMessageBroadcast.replace("[Player]", Vars.target.getName());
					Bukkit.broadcastMessage(banbroadcast);
				}
				break;
			case 34:
				SoundsFeature.randomScarySound(Vars.target);
				break;
			case 38:
				RocketFeature.rocket(Vars.target);
				break;
			}
		}
	}
}
