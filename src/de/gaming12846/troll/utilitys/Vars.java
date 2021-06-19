/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.utilitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.gaming12846.troll.main.Main;

public class Vars {
	public static Player target;
	public static Inventory trollmenu;

	public static FileConfiguration blacklist = null;

	// Plugin initialisation
	public static String prefix = "§f[§dTroll§f] ";

	// Messages
	public static String noPermission = prefix + Main.getPlugin().getConfig().getString("messages.noPermission");
	public static String noConsole = "[Troll] " + Main.getPlugin().getConfig().getString("messages.noConsole");

	public static String usageTrollmenu = prefix + Main.getPlugin().getConfig().getString("messages.usageTrollmenu");
	public static String usageBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.usageBlacklist");;

	public static String targetNotOnline = prefix + Main.getPlugin().getConfig().getString("messages.targetNotOnline");
	public static String immune = prefix + Main.getPlugin().getConfig().getString("messages.immune");

	public static String banMessagePlayer = Main.getPlugin().getConfig().getString("banmessage.banmessageplayer");
	public static String banMessageBroadcast = Main.getPlugin().getConfig().getString("banmessage.banmessagebroadcast");

	public static String addedBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.addedBlacklist");
	public static String removedBlacklist = prefix
			+ Main.getPlugin().getConfig().getString("messages.removedBlacklist");
	public static String allreadyInBlacklist = prefix
			+ Main.getPlugin().getConfig().getString("messages.allreadyInBlacklist");
	public static String notInBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.notInBlacklist");

	// Booleans
	public static boolean bankMessageBroadcastOnOff = Main.getPlugin().getConfig()
			.getBoolean("banmessage.bankmessagebroadcastonoff");

	// ArrayLists
	public static ArrayList<String> vanishList = new ArrayList<>();
	public static ArrayList<String> freezeList = new ArrayList<>();
	public static ArrayList<String> handitemdropList = new ArrayList<>();
	public static ArrayList<String> controlList = new ArrayList<>();
	public static ArrayList<String> spammessagesList = new ArrayList<>();
	public static ArrayList<String> spamsoundsList = new ArrayList<>();
	public static ArrayList<String> flipbehindList = new ArrayList<>();

	// Lists
	public static List<String> spammessages = Main.getPlugin().getConfig().getStringList("spammessages");

	// HashMaps
	public static HashMap<Player, Player> controller = new HashMap<>();

	// Status
	public static String vanishStatus = "§cOFF";
	public static String freezeStatus = "§cOFF";
	public static String handitemdropStatus = "§cOFF";
	public static String controlStatus = "§cOFF";
	public static String spammessagesStatus = "§cOFF";
	public static String spamsoundsStatus = "§cOFF";
	public static String flipbehindStatus = "§cOFF";
}
