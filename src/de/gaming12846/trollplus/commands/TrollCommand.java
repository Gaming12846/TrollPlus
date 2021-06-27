/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.gaming12846.trollplus.utilitys.ConfigLoader;
import de.gaming12846.trollplus.utilitys.ItemBuilder;
import de.gaming12846.trollplus.utilitys.Vars;
import net.md_5.bungee.api.ChatColor;

public class TrollCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("troll")) {

			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (p.hasPermission("troll.opentrollmenu")) {

					if (args.length == 1) {
						Player t = Bukkit.getPlayer(args[0]);

						if (t != null) {

							if (!ConfigLoader.getBlacklist().contains(t.getUniqueId().toString())) {
								Vars.target = t;

								// Create inventory
								Vars.trollmenu = Bukkit.createInventory(null, 54, "Troll §c§l" + Vars.target.getName());

								// Basic things
								Vars.trollmenu.setItem(53, ItemBuilder.createItem(Material.BARRIER, 0, ChatColor.GRAY + "Close"));
								Vars.trollmenu.setItem(51, ItemBuilder.createItem(Material.CHEST, 0, ChatColor.WHITE + "Invsee"));
								Vars.trollmenu.setItem(50, ItemBuilder.createItem(Material.WITHER_SKELETON_SKULL, 0, ChatColor.WHITE + "Kill"));
								Vars.trollmenu.setItem(48, ItemBuilder.createItem(Material.ENDER_PEARL, 0, ChatColor.WHITE + "Teleport to player"));
								Vars.trollmenu.setItem(47,
										ItemBuilder.createItem(Material.POTION, 0, ChatColor.WHITE + "Vanish " + Vars.Status.vanishStatus));
								Vars.trollmenu.setItem(4,
										ItemBuilder.createSkull(3, ChatColor.RED + t.getName() + ChatColor.BOLD, t.getName().toString()));

								// Features
								Vars.trollmenu.setItem(10,
										ItemBuilder.createItem(Material.ICE, 0, ChatColor.WHITE + "Freeze " + Vars.Status.freezeStatus));
								Vars.trollmenu.setItem(12, ItemBuilder.createItem(Material.SHEARS, 0,
										ChatColor.WHITE + "Hand item drop " + Vars.Status.handItemDropStatus));
								Vars.trollmenu.setItem(14,
										ItemBuilder.createItem(Material.LEAD, 0, ChatColor.WHITE + "Control " + Vars.Status.controlStatus));
								Vars.trollmenu.setItem(16,
										ItemBuilder.createItem(Material.COMPASS, 0, ChatColor.WHITE + "Flip behind " + Vars.Status.flipBehindStatus));
								Vars.trollmenu.setItem(20, ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
										ChatColor.WHITE + "Spam messages " + Vars.Status.spamMessagesStatus));
								Vars.trollmenu.setItem(22, ItemBuilder.createItem(Material.NOTE_BLOCK, 0,
										ChatColor.WHITE + "Spam sounds " + Vars.Status.spamSoundsStatus));
								Vars.trollmenu.setItem(24,
										ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 0, ChatColor.WHITE + "Semi ban " + Vars.Status.semiBanStatus));
								Vars.trollmenu.setItem(28,
										ItemBuilder.createItem(Material.TNT, 0, ChatColor.WHITE + "TNT track " + Vars.Status.tntTrackStatus));
								Vars.trollmenu.setItem(30,
										ItemBuilder.createItem(Material.SPAWNER, 0, ChatColor.WHITE + "Mob spawner " + Vars.Status.mobSpawnerStatus));
								Vars.trollmenu.setItem(32, ItemBuilder.createItem(Material.PAPER, 0, ChatColor.WHITE + "Fake ban"));
								Vars.trollmenu.setItem(34, ItemBuilder.createItem(Material.MUSIC_DISC_11, 0, ChatColor.WHITE + "Random scary sound"));

								// Placeholder
								int[] placeholderArray = new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 49, 52 };
								for (int slot : placeholderArray) {
									Vars.trollmenu.setItem(slot, ItemBuilder.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								}

								p.openInventory(Vars.trollmenu);
							} else {
								String immune = Vars.Messages.immune.replace("[Player]", ChatColor.BOLD + args[0] + ChatColor.RESET);
								p.sendMessage(immune);
							}
						} else {
							String notOnline = Vars.Messages.targetNotOnline.replace("[Player]", ChatColor.BOLD + args[0] + ChatColor.RESET);
							p.sendMessage(notOnline);
						}
					} else
						p.sendMessage(Vars.Messages.usageTrollmenu);
				} else
					p.sendMessage(Vars.Messages.noPermission);
			} else
				sender.sendMessage(Vars.Messages.noConsole);
		}
		return true;
	}
}
