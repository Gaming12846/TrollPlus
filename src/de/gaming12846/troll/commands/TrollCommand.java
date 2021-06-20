/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.gaming12846.troll.utilitys.ConfigLoader;
import de.gaming12846.troll.utilitys.Items;
import de.gaming12846.troll.utilitys.Vars;

public class TrollCommand implements CommandExecutor {

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("troll")) {

			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (p.hasPermission("troll.opentrollmenu")) {

					if (args.length == 1) {
						Player t = Bukkit.getPlayer(args[0]);

						if (!ConfigLoader.getBlacklist().contains(t.getUniqueId().toString())) {

							if (t != null) {
								Vars.target = t;

								// Create inventory
								Vars.trollmenu = Bukkit.createInventory(null, 27, "Troll §4" + Vars.target.getName());

								/**
								 * | Freeze | x | Handitemdrop | x | Control | x | Flip behind | x | Spam sounds |
								 * 
								 * | Spam messages | x | Random Scray sound | x | TNT track | x | Semi ban | x | Fake ban |
								 * 
								 * | Vanish | x | Teleport | x | Kill | x | Invsee | x | Close |
								 */

								// Basic things
								Vars.trollmenu.setItem(26, Items.createItem(Material.BARRIER, 0, "§fClose"));
								Vars.trollmenu.setItem(24, Items.createItem(Material.CHEST, 0, "§fInvsee"));
								Vars.trollmenu.setItem(22,
										Items.createItem(Material.WITHER_SKELETON_SKULL, 0, "§fKill"));
								Vars.trollmenu.setItem(20,
										Items.createItem(Material.ENDER_PEARL, 0, "§fTeleport to player"));
								Vars.trollmenu.setItem(18,
										Items.createItem(Material.POTION, 0, "§fVanish " + Vars.vanishStatus));

								// Features
								Vars.trollmenu.setItem(0,
										Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezeStatus));
								Vars.trollmenu.setItem(2, Items.createItem(Material.FEATHER, 0,
										"§fHand item drop " + Vars.handitemdropStatus));
								Vars.trollmenu.setItem(4,
										Items.createItem(Material.LEAD, 0, "§fControl " + Vars.controlStatus));
								Vars.trollmenu.setItem(6,
										Items.createItem(Material.EGG, 0, "§fFlip behind " + Vars.flipbehindStatus));
								Vars.trollmenu.setItem(8, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
										"§fSpam sounds " + Vars.spamsoundsStatus));
								Vars.trollmenu.setItem(9, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
										"§fSpam messages " + Vars.spammessagesStatus));
								Vars.trollmenu.setItem(11,
										Items.createItem(Material.NOTE_BLOCK, 0, "§fPlay random scary sound"));
								Vars.trollmenu.setItem(17, Items.createItem(Material.PAPER, 0, "§fFake ban"));
								Vars.trollmenu.setItem(15,
										Items.createItem(Material.SHEARS, 0, "§fSemi ban " + Vars.semiBanStatus));
								Vars.trollmenu.setItem(13,
										Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.tntTrackStatus));

								p.openInventory(Vars.trollmenu);
							} else {
								String notOnline = "";
								notOnline = Vars.targetNotOnline.replace("[Player]", args[0]);
								p.sendMessage(notOnline);
							}
						} else {
							String immune = "";
							immune = Vars.immune.replace("[Player]", args[0]);
							p.sendMessage(immune);
						}
					} else
						p.sendMessage(Vars.usageTrollmenu);
				} else
					p.sendMessage(Vars.noPermission);
			} else
				sender.sendMessage(Vars.noConsole);
		}
		return true;
	}
}
