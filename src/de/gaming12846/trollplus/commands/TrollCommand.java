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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.gaming12846.trollplus.utilitys.ConfigLoader;
import de.gaming12846.trollplus.utilitys.Items;
import de.gaming12846.trollplus.utilitys.Vars;

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
								Vars.trollmenu = Bukkit.createInventory(null, 54, "Troll §l§c" + Vars.target.getName());

								// Basic things
								Vars.trollmenu.setItem(53, Items.createItem(Material.BARRIER, 0, "§7Close"));
								Vars.trollmenu.setItem(51, Items.createItem(Material.CHEST, 0, "§fInvsee"));
								Vars.trollmenu.setItem(50, Items.createItem(Material.WITHER_SKELETON_SKULL, 0, "§fKill"));
								Vars.trollmenu.setItem(48, Items.createItem(Material.ENDER_PEARL, 0, "§fTeleport to player"));
								Vars.trollmenu.setItem(47, Items.createItem(Material.POTION, 0, "§fVanish " + Vars.Status.vanishStatus));
								ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
								SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
								skullmeta.setOwner(t.getName());
								skullmeta.setDisplayName("§l§c" + t.getName());
								skull.setItemMeta(skullmeta);
								Vars.trollmenu.setItem(4, skull);

								// Features
								Vars.trollmenu.setItem(10, Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.Status.freezeStatus));
								Vars.trollmenu.setItem(12,
										Items.createItem(Material.SHEARS, 0, "§fHand item drop " + Vars.Status.handitemdropStatus));
								Vars.trollmenu.setItem(14, Items.createItem(Material.LEAD, 0, "§fControl " + Vars.Status.controlStatus));
								Vars.trollmenu.setItem(16,
										Items.createItem(Material.COMPASS, 0, "§fFlip behind " + Vars.Status.flipbehindStatus));
								Vars.trollmenu.setItem(20, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
										"§fSpam messages " + Vars.Status.spammessagesStatus));
								Vars.trollmenu.setItem(22,
										Items.createItem(Material.NOTE_BLOCK, 0, "§fSpam sounds " + Vars.Status.spamsoundsStatus));
								Vars.trollmenu.setItem(24,
										Items.createItem(Material.TRIPWIRE_HOOK, 0, "§fSemi ban " + Vars.Status.semibanStatus));
								Vars.trollmenu.setItem(28, Items.createItem(Material.TNT, 0, "§fTNT track " + Vars.Status.tnttrackStatus));
								Vars.trollmenu.setItem(30, Items.createItem(Material.PAPER, 0, "§fFake ban"));
								Vars.trollmenu.setItem(32, Items.createItem(Material.MUSIC_DISC_11, 0, "§fRandom scary sound"));

								// Placeholder
								Vars.trollmenu.setItem(0, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(1, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(2, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(3, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(5, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(6, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(7, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(8, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(9, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(17, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(18, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(26, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(27, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(35, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(36, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(44, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(45, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(46, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(49, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));
								Vars.trollmenu.setItem(52, Items.createItem(Material.RED_STAINED_GLASS_PANE, 0, " "));

								p.openInventory(Vars.trollmenu);
							} else {
								String immune = "";
								immune = Vars.Messages.immune.replace("[Player]", "§l" + args[0] + "§r");
								p.sendMessage(immune);
							}
						} else {
							String notOnline = "";
							notOnline = Vars.Messages.targetNotOnline.replace("[Player]", "§l" + args[0] + "§r");
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
